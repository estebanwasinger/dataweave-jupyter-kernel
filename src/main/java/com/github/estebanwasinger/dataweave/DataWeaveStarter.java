package com.github.estebanwasinger.dataweave;

import io.github.spencerpark.jupyter.channels.JupyterConnection;
import io.github.spencerpark.jupyter.channels.JupyterSocket;
import io.github.spencerpark.jupyter.kernel.KernelConnectionProperties;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class DataWeaveStarter {
    public static final String COMPILER_OPTS_KEY = "IJAVA_COMPILER_OPTS";
    public static final String TIMEOUT_DURATION_KEY = "IJAVA_TIMEOUT";
    public static final String CLASSPATH_KEY = "IJAVA_CLASSPATH";
    public static final String STARTUP_SCRIPTS_KEY = "IJAVA_STARTUP_SCRIPTS_PATH";
    public static final String STARTUP_SCRIPT_KEY = "IJAVA_STARTUP_SCRIPT";

    public static final String DEFAULT_SHELL_INIT_RESOURCE_PATH = "ijava-jshell-init.jshell";

    public static final String VERSION ="2.0";

    public static InputStream resource(String path) {
        return DataWeaveKernel.class.getClassLoader().getResourceAsStream(path);
    }


    private static DataWeaveKernel kernel = null;

    /**
     * Obtain a reference to the kernel created by running {@link #main(String[])}. This
     * kernel may be null if one is not present but as the main use for this method is
     * for the kernel user code to access kernel services.
     *
     * @return the kernel created by running {@link #main(String[])} or {@code null} if
     *         one has not yet (or already created and finished) been created.
     */
    public static DataWeaveKernel getKernelInstance() {
        return DataWeaveStarter.kernel;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1)
            throw new IllegalArgumentException("Missing connection file argument");

        Path connectionFile = Paths.get(args[0]);

        if (!Files.isRegularFile(connectionFile))
            throw new IllegalArgumentException("Connection file '" + connectionFile + "' isn't a file.");

        String contents = new String(Files.readAllBytes(connectionFile));

        JupyterSocket.JUPYTER_LOGGER.setLevel(Level.WARNING);

        KernelConnectionProperties connProps = KernelConnectionProperties.parse(contents);
        JupyterConnection connection = new JupyterConnection(connProps);

        kernel = new DataWeaveKernel();
        kernel.becomeHandlerForConnection(connection);

        connection.connect();
        connection.waitUntilClose();

        kernel = null;

        System.exit(0);
    }
}
