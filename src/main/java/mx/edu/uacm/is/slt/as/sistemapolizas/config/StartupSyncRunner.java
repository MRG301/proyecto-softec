package mx.edu.uacm.is.slt.as.sistemapolizas.config;

import mx.edu.uacm.is.slt.as.sistemapolizas.service.SincronizacionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class StartupSyncRunner implements CommandLineRunner{

    private final SincronizacionService sync;

    @Override
    public void run(String... args) {
        sync.sincronizarTodo();
        System.out.println("✅ BD local sincronizada con el sistema dueño");
    }
}
