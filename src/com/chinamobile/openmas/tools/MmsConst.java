/**
 * 
 */
package com.chinamobile.openmas.tools;

/**
 * @author OpenMas
 *
 */
public class MmsConst 
{
	/**
	 * TEXT
	 */
	public static final  MmsContentType TEXT = new MmsContentType("text/plain");
	/**
	 * XML
	 */
    public static final MmsContentType XML = new MmsContentType("text/xml");
    /**
     * SMIL
     */
    public static final MmsContentType SMIL = new MmsContentType("application/smil");
    /**
     * AMR
     */
    public static final MmsContentType AMR = new MmsContentType("audio/amr");
    /**
     * MIDI
     */
    public static final MmsContentType MIDI = new MmsContentType("audio/midi");
    /**
     * GIF
     */
    public static final MmsContentType GIF = new MmsContentType("image/gif");
    /**
     * JPG
     */
    public static final MmsContentType JPEG = new MmsContentType("image/jpeg");
    /**
     * WBMP
     */
    public static final MmsContentType WBMP = new MmsContentType("image/vnd.wap.wbmp");
    /**
     * PNG
     */
    public static final MmsContentType PNG = new MmsContentType("image/png");
    /**
     * multipart/related
     */
    public static final MmsContentType MULTIPART_RELATED = new MmsContentType("multipart/related;start=<START>");
    /**
     * multipart/mixed
     */
    public static final MmsContentType MULTIPART_MIXED = new MmsContentType("multipart/mixed");
}
