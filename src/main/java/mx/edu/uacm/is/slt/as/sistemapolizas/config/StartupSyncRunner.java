package mx.edu.uacm.is.slt.as.sistemapolizas.config;

import mx.edu.uacm.is.slt.as.sistemapolizas.service.SincronizacionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class StartupSyncRunner implements CommandLineRunner{

    private final SincronizacionService sync;
    private static final Logger log = LoggerFactory.getLogger(StartupSyncRunner.class);

    @Override
    public void run(String... args) {
        sync.sincronizarTodo();
        log.info("✅ BD local sincronizada con el sistema dueño");
    }
}
