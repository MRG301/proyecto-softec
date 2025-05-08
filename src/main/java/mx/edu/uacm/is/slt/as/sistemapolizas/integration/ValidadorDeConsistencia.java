package mx.edu.uacm.is.slt.as.sistemapolizas.integration;

import mx.edu.uacm.is.slt.as.sistemapolizas.exception.InconsistenciaDatosException;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistemapolizas.model.Poliza;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ValidadorDeConsistencia {
/**
    // Constructor privado para prevenir instanciación de la clase de utilidad
    private ValidadorDeConsistencia() { }

    /**
     * Validación 1: Dado un ID de póliza, verifica que exista en ambos sistemas con los mismos datos.
     * @param polizaId Identificador UUID de la póliza a verificar.
     * @param polizaLocal Objeto Póliza obtenido del repositorio local (puede ser null si no existe).
     * @param polizaRemota Objeto Póliza equivalente del sistema remoto (simulado, puede ser null).
     * @throws InconsistenciaDatosException si la póliza no existe en alguno de los repositorios
     *         o si sus atributos, cliente o beneficiarios difieren.
     *//**
    public static void validarPolizaPorId(UUID polizaId, Poliza polizaLocal, Poliza polizaRemota) {
        // Verificar existencia de la póliza en ambos repositorios
        if (polizaLocal == null) {
            throw new InconsistenciaDatosException("La póliza con ID " + polizaId + " no existe en el sistema local.");
        }
        if (polizaRemota == null) {
            throw new InconsistenciaDatosException("La póliza con ID " + polizaId + " no existe en el sistema remoto.");
        }
        // (En un caso real, aquí se podría haber obtenido polizaLocal via JPA y polizaRemota via REST usando polizaId)

        // Verificar que el ID coincida en ambos (consistencia de identificador)
        if (!polizaLocal.getId().equals(polizaRemota.getId())) {
            throw new InconsistenciaDatosException("Inconsistencia: el ID de la póliza local ("
                    + polizaLocal.getId() + ") difiere del ID remoto (" + polizaRemota.getId() + ").");
        }

        // Comparar atributos básicos de la póliza (ejemplo: número/clave de póliza)
        if (!Objects.equals(polizaLocal.getNumeroPoliza(), polizaRemota.getNumeroPoliza())) {
            throw new InconsistenciaDatosException("Inconsistencia en póliza " + polizaId
                    + ": el número de póliza difiere (local=" + polizaLocal.getNumeroPoliza()
                    + ", remoto=" + polizaRemota.getNumeroPoliza() + ").");
        }
        // (Comparar otros atributos relevantes de Poliza de forma similar, e.g., fechas, monto, estado, etc.)

        // Verificar consistencia del cliente asociado a la póliza
        Cliente clienteLocal = polizaLocal.getCliente();
        Cliente clienteRemoto = polizaRemota.getCliente();
        if (clienteLocal == null || clienteRemoto == null) {
            throw new InconsistenciaDatosException("Inconsistencia en póliza " + polizaId
                    + ": asociación de cliente nula en uno de los sistemas.");
        }
        // Comparar identificador único del cliente (por ejemplo, CURP)
        if (!Objects.equals(clienteLocal.getCurp(), clienteRemoto.getCurp())) {
            throw new InconsistenciaDatosException("Inconsistencia en póliza " + polizaId
                    + ": el cliente asociado no coincide (CURP local=" + clienteLocal.getCurp()
                    + ", CURP remoto=" + clienteRemoto.getCurp() + ").");
        }
        // (Opcional: comparar también otros atributos del cliente, p. ej. nombre, si se desea estricta igualdad)

        // Verificar consistencia de la lista de beneficiarios
        List<Beneficiario> benLocalList = polizaLocal.getBeneficiarios();
        List<Beneficiario> benRemotaList = polizaRemota.getBeneficiarios();
        if (benLocalList == null) benLocalList = new ArrayList<>();
        if (benRemotaList == null) benRemotaList = new ArrayList<>();
        // Verificar mismo número de beneficiarios
        if (benLocalList.size() != benRemotaList.size()) {
            throw new InconsistenciaDatosException("Inconsistencia en póliza " + polizaId
                    + ": número de beneficiarios difiere (local=" + benLocalList.size()
                    + ", remoto=" + benRemotaList.size() + ").");
        }
        // Verificar que cada beneficiario local tenga equivalente remoto
        for (Beneficiario benLocal : benLocalList) {
            Beneficiario matchRemoto = benRemotaList.stream()
                    .filter(ben -> Objects.equals(ben.getNombreCompleto(), benLocal.getNombreCompleto())
                            && Objects.equals(ben.getFechaNacimiento(), benLocal.getFechaNacimiento()))
                    .findFirst().orElse(null);
            if (matchRemoto == null) {
                throw new InconsistenciaDatosException("Inconsistencia en póliza " + polizaId
                        + ": el beneficiario '" + benLocal.getNombreCompleto() + " (" + benLocal.getFechaNacimiento()
                        + ")' existe localmente pero no en el sistema remoto.");
            }
            // Opcional: verificar otros atributos del beneficiario si los hay (por ejemplo, parentesco)
            if (!Objects.equals(matchRemoto.getNombreCompleto(), benLocal.getNombreCompleto()) ||
                    !Objects.equals(matchRemoto.getFechaNacimiento(), benLocal.getFechaNacimiento())) {
                throw new InconsistenciaDatosException("Inconsistencia en póliza " + polizaId
                        + ": datos del beneficiario '" + benLocal.getNombreCompleto() + "' difieren entre sistemas.");
            }
        }
        // También asegurar que no haya beneficiarios en remoto que no estén en local (dado que tamaños son iguales, la comprobación anterior cubre esto).
    }

    /**
     * Validación 2: Dada una instancia de Póliza, verifica que su registro sea igual en ambos repositorios.
     * @param polizaLocal Objeto Póliza del sistema local.
     * @param polizaRemota Objeto Póliza correspondiente del sistema remoto.
     * @throws InconsistenciaDatosException si se encuentra cualquier diferencia en los atributos de la póliza,
     *         su cliente asociado o sus beneficiarios.
     *//**
    public static void validarPoliza(Poliza polizaLocal, Poliza polizaRemota) {
        if (polizaLocal == null || polizaRemota == null) {
            throw new InconsistenciaDatosException("La póliza proporcionada es nula en alguno de los sistemas.");
        }
        // Podemos reutilizar la validación por ID asumiendo que las pólizas tienen su identificador establecido
        validarPolizaPorId(polizaLocal.getId(), polizaLocal, polizaRemota);
    }

    /**
     * Validación 3: Dado un CURP de cliente, verifica que el cliente exista en ambos sistemas con las mismas pólizas y beneficiarios.
     * @param curp CURP del cliente a validar.
     * @param clienteLocal Objeto Cliente obtenido del repositorio local (puede ser null si no existe).
     * @param clienteRemoto Objeto Cliente equivalente del sistema remoto (simulado).
     * @throws InconsistenciaDatosException si el cliente no existe en alguno de los sistemas o si hay discrepancias
     *         en sus atributos o en sus relaciones (pólizas/beneficiarios asociados).
     *//**
    public static void validarClientePorCurp(String curp, Cliente clienteLocal, Cliente clienteRemoto) {
        // Verificar existencia del cliente en ambos sistemas
        if (clienteLocal == null) {
            throw new InconsistenciaDatosException("El cliente con CURP " + curp + " no existe en el sistema local.");
        }
        if (clienteRemoto == null) {
            throw new InconsistenciaDatosException("El cliente con CURP " + curp + " no existe en el sistema remoto.");
        }
        // (En un caso real, aquí se habría buscado clienteLocal en la BD local y clienteRemoto vía servicio remoto usando el CURP)

        // Verificar que el CURP coincida en ambos objetos (consistencia de clave)
        if (!Objects.equals(clienteLocal.getCurp(), clienteRemoto.getCurp())) {
            throw new InconsistenciaDatosException("Inconsistencia: el CURP del cliente local (" + clienteLocal.getCurp()
                    + ") difiere del CURP remoto (" + clienteRemoto.getCurp() + ").");
        }

        // Comparar atributos básicos del cliente (ejemplo: nombre completo, correo, etc. según definición de Cliente)
        if (!Objects.equals(clienteLocal.getNombreCompleto(), clienteRemoto.getNombreCompleto())) {
            throw new InconsistenciaDatosException("Inconsistencia en cliente CURP " + curp
                    + ": el nombre completo difiere (local=\"" + clienteLocal.getNombreCompleto()
                    + "\", remoto=\"" + clienteRemoto.getNombreCompleto() + "\").");
        }
        // (Comparar otros campos del cliente si aplica: teléfono, dirección, etc.)

        // Verificar consistencia de las pólizas asociadas al cliente
        List<Poliza> polizasLocal = clienteLocal.getPolizas();
        List<Poliza> polizasRemoto = clienteRemoto.getPolizas();
        if (polizasLocal == null) polizasLocal = new ArrayList<>();
        if (polizasRemoto == null) polizasRemoto = new ArrayList<>();
        if (polizasLocal.size() != polizasRemoto.size()) {
            throw new InconsistenciaDatosException("Inconsistencia en cliente CURP " + curp
                    + ": número de pólizas difiere (local=" + polizasLocal.size()
                    + ", remoto=" + polizasRemoto.size() + ").");
        }
        // Verificar cada póliza del cliente
        for (Poliza polizaLoc : polizasLocal) {
            // Buscar póliza correspondiente en la lista remota (mismo ID o misma clave de póliza)
            Poliza polizaMatch = polizasRemoto.stream()
                    .filter(pol -> Objects.equals(pol.getId(), polizaLoc.getId())
                            || Objects.equals(pol.getNumeroPoliza(), polizaLoc.getNumeroPoliza()))
                    .findFirst().orElse(null);
            if (polizaMatch == null) {
                throw new InconsistenciaDatosException("Inconsistencia en cliente CURP " + curp
                        + ": la póliza " + polizaLoc.getNumeroPoliza() + " asociada existe localmente pero no en remoto.");
            }
            // Verificar que la póliza coincida en detalles, reutilizando validación de póliza
            try {
                validarPoliza(polizaLoc, polizaMatch);
            } catch (InconsistenciaDatosException e) {
                // Agregar contexto del cliente al mensaje y relanzar
                throw new InconsistenciaDatosException("Inconsistencia en cliente CURP " + curp
                        + ": diferencia en datos de la póliza " + polizaLoc.getNumeroPoliza() + " -> " + e.getMessage());
            }
        }
        // Asegurar que no haya pólizas en remoto no encontradas en local (ya cubierto por tamaño igual y búsqueda inversa implícitamente)
    }

    /**
     * Validación 4: Dado un objeto Cliente, verifica que exista en el sistema remoto con los mismos datos y relaciones.
     * @param clienteLocal Objeto Cliente del sistema local.
     * @param clienteRemoto Objeto Cliente correspondiente del sistema remoto.
     * @throws InconsistenciaDatosException si hay alguna diferencia en los campos del cliente o en sus pólizas/beneficiarios asociados.
     *//**
    public static void validarCliente(Cliente clienteLocal, Cliente clienteRemoto) {
        if (clienteLocal == null || clienteRemoto == null) {
            throw new InconsistenciaDatosException("El objeto Cliente proporcionado es nulo en alguno de los sistemas.");
        }
        // Podemos reutilizar la validación por CURP asumiendo que ambos clientes tienen CURP
        validarClientePorCurp(clienteLocal.getCurp(), clienteLocal, clienteRemoto);
    }

    /**
     * Validación 5: Verifica un beneficiario por nombre, fecha de nacimiento y clave de póliza.
     * @param nombreCompleto Nombre completo del beneficiario a verificar.
     * @param fechaNacimiento Fecha de nacimiento del beneficiario.
     * @param clavePoliza Clave o número identificador de la póliza de la cual es beneficiario.
     * @param beneficiarioLocal Objeto Beneficiario obtenido del sistema local (puede ser null si no existe).
     * @param beneficiarioRemoto Objeto Beneficiario correspondiente del sistema remoto (puede ser null).
     * @throws InconsistenciaDatosException si el beneficiario no existe en alguno de los sistemas,
     *         o si sus datos (nombre, fechaNacimiento u otros atributos) o la referencia a la póliza difieren.
     *//**
    public static void validarBeneficiario(String nombreCompleto, LocalDate fechaNacimiento, String clavePoliza,
                                           Beneficiario beneficiarioLocal, Beneficiario beneficiarioRemoto) {
        // Verificar existencia en ambos sistemas
        if (beneficiarioLocal == null) {
            throw new InconsistenciaDatosException("El beneficiario \"" + nombreCompleto + "\" (nac. "
                    + fechaNacimiento + ", póliza " + clavePoliza + ") no existe en el sistema local.");
        }
        if (beneficiarioRemoto == null) {
            throw new InconsistenciaDatosException("El beneficiario \"" + nombreCompleto + "\" (nac. "
                    + fechaNacimiento + ", póliza " + clavePoliza + ") no existe en el sistema remoto.");
        }
        // Verificar coincidencia de nombre
        if (!Objects.equals(beneficiarioLocal.getNombreCompleto(), beneficiarioRemoto.getNombreCompleto())) {
            throw new InconsistenciaDatosException("Inconsistencia en beneficiario \"" + nombreCompleto
                    + "\": el nombre no coincide (local=\"" + beneficiarioLocal.getNombreCompleto()
                    + "\", remoto=\"" + beneficiarioRemoto.getNombreCompleto() + "\").");
        }
        // Verificar coincidencia de fecha de nacimiento
        if (!Objects.equals(beneficiarioLocal.getFechaNacimiento(), beneficiarioRemoto.getFechaNacimiento())) {
            throw new InconsistenciaDatosException("Inconsistencia en beneficiario \"" + nombreCompleto
                    + "\": la fecha de nacimiento no coincide (local=" + beneficiarioLocal.getFechaNacimiento()
                    + ", remoto=" + beneficiarioRemoto.getFechaNacimiento() + ").");
        }
        // Verificar coincidencia de la póliza asociada (usar la clave o número de póliza como identificador)
        Poliza polizaLocal = beneficiarioLocal.getPoliza();
        Poliza polizaRemota = beneficiarioRemoto.getPoliza();
        String clavePolizaLocal = (polizaLocal != null ? polizaLocal.getNumeroPoliza() : null);
        String clavePolizaRemota = (polizaRemota != null ? polizaRemota.getNumeroPoliza() : null);
        if (!Objects.equals(clavePolizaLocal, clavePoliza) || !Objects.equals(clavePolizaRemota, clavePoliza)) {
            throw new InconsistenciaDatosException("Inconsistencia: el beneficiario \"" + nombreCompleto
                    + "\" no está asociado a la póliza " + clavePoliza + " en ambos sistemas (local->" + clavePolizaLocal
                    + ", remoto->" + clavePolizaRemota + ").");
        }
        // (Opcional: comparar otros atributos del beneficiario si existen, para asegurar que todos los datos coinciden)
    }
    */
}
