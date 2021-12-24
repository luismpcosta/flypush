package io.opensw.flypush.api.utils;

import java.math.BigDecimal;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class CheckSumUtils {

	/**
	 * Create checksum of byte array
	 * 
	 * @param str to create checksum
	 * @return long checksum
	 */
	public static Long checksum( String str ) {
		if ( str == null ) {
			return null;
		}
		return checksum( str.getBytes() );
	}

	/**
	 * Create checksum of byte array
	 * 
	 * @param bytes to create checksum
	 * @return long checksum
	 */
	public static Long checksum( byte [] bytes ) {
		if ( bytes == null ) {
			return null;
		}

		Checksum crc32 = new CRC32();
		crc32.update( bytes, 0, bytes.length );
		return crc32.getValue();
	}

	/**
	 * Create checksum of byte array
	 * 
	 * @param str to create checksum
	 * @return bigdecimal checksum
	 */
	public static BigDecimal checksumBigdecimal( String str ) {
		if ( str == null ) {
			return null;
		}
		return checksumBigdecimal( str.getBytes() );
	}
	
	/**
	 * Create checksum of byte array
	 * 
	 * @param bytes to create checksum
	 * @return bigdecimal checksum
	 */
	public static BigDecimal checksumBigdecimal( byte [] bytes ) {
		if ( bytes == null ) {
			return null;
		}

		Long checksum = checksum( bytes );
		return checksum != null ? BigDecimal.valueOf( checksum ) : null;
	}

}
