package ar.com.simore.simoreapi.scheduler.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class LockFileUtils {
	private static final String VERIFICANDO_EXISTENCIA_DE_ARCHIVO_INDICADOR_DE_INSTANCIA_UNICA = "Verifying existence of unique file...";
	private static final String VERIFICACION_EXITOSA = "Sucessfull verification";
	private static final String NO_SE_PUDO_CREAR_EL_LOCK_DE_INSTANCIA_UNICA = "Could not create lock of unique file";
	private static final String NO_SE_PUEDE_INICIAR_UNA_NUEVA_INSTANCIA_DEBIDO_A_QUE_YA_EXISTE_EL_ARCHIVO_S = "No se puede iniciar una nueva instancia debido a que ya existe el archivo %s";
	private static final String NO_SE_PUDO_ELIMINAR_EL_ARCHIVO_S = "No se pudo eliminar el archivo %s";
	private static final Logger logger = Logger.getLogger(LockFileUtils.class.getName());

	/**
	 * Verifies the existence lock file
	 *
	 * @param lockFile
	 * @return
	 * @throws IOException
	 */
	public static boolean verifyLockFile(final String lockFile) throws IOException {
		// Verifico la existencia del archivo .lock
		logger.info(VERIFICANDO_EXISTENCIA_DE_ARCHIVO_INDICADOR_DE_INSTANCIA_UNICA);
		final File lock = new File(lockFile);
		if (!lock.exists()) {
			if(lock.createNewFile()){
				logger.info(VERIFICACION_EXITOSA);
				return true;
			}else{
				logger.error(NO_SE_PUDO_CREAR_EL_LOCK_DE_INSTANCIA_UNICA);
				return false;
			}
		} else {
			logger.warn(String.format(NO_SE_PUEDE_INICIAR_UNA_NUEVA_INSTANCIA_DEBIDO_A_QUE_YA_EXISTE_EL_ARCHIVO_S, lockFile));
			return false;
		}
	}

	public static void removeLockFile(final String lockFile) {
		final File lock = new File(lockFile);
		if (!lock.delete() && lock.exists()) {
			logger.error(String.format(NO_SE_PUDO_ELIMINAR_EL_ARCHIVO_S, lock));
		}
	}

}
