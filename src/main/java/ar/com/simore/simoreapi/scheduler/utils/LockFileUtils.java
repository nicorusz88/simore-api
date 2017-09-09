package ar.com.simore.simoreapi.scheduler.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class LockFileUtils {
	private static final String VERIFYING_EXISTENCE_OF_UNIQUE_FILE = "Verifying existence of unique file...";
	private static final String SUCESSFUL_VERIFICATION = "Sucessful verification";
	private static final String COULD_NOT_CREATE_LOCK_OF_UNIQUE_FILE = "Could not create lock of unique file";
	private static final String INSTANCE_CANNOT_START_SINCE_UNIQUE_LOCK_FILE_ALREADY_EXISTS_S = "Instance cannot start since unique lock file already exists %s";
	private static final String UNIQUE_LOCK_FILE_CANNOT_BE_DELETED_S = "Unique lock file cannot be deleted %s";
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
		logger.info(VERIFYING_EXISTENCE_OF_UNIQUE_FILE);
		final File lock = new File(lockFile);
		if (!lock.exists()) {
			if(lock.createNewFile()){
				logger.info(SUCESSFUL_VERIFICATION);
				return true;
			}else{
				logger.error(COULD_NOT_CREATE_LOCK_OF_UNIQUE_FILE);
				return false;
			}
		} else {
			logger.warn(String.format(INSTANCE_CANNOT_START_SINCE_UNIQUE_LOCK_FILE_ALREADY_EXISTS_S, lockFile));
			return false;
		}
	}

	public static void removeLockFile(final String lockFile) {
		final File lock = new File(lockFile);
		if (!lock.delete() && lock.exists()) {
			logger.error(String.format(UNIQUE_LOCK_FILE_CANNOT_BE_DELETED_S, lock));
		}
	}

}
