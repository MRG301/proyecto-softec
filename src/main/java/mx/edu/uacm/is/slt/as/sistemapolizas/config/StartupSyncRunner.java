package mx.edu.uacm.is.slt.as.sistemapolizas.config;

public class StartupSyncRunner {
    private final SincronizacionService sync;

    @Override
    public void run(String... args) {
        sync.sincronizarTodo();
        System.out.println("✅  BD local sincronizada con el sistema dueño.");
    }
}
