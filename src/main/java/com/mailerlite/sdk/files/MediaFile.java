/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.files;

import com.google.gson.annotations.SerializedName;

/**
 * A file in the media library of the account. Use its URL in image blocks.
 */
public class MediaFile {

	/** The ID of the file. */
	@SerializedName("id")
	public String id;

	/** The file type, for example "image". See MediaFileTypes. The SDK accepts any value. */
	@SerializedName("type")
	public String type;

	/** The file name. */
	@SerializedName("name")
	public String name;

	/** The public URL of the file. */
	@SerializedName("url")
	public String url;

	/** The size of the file in bytes. */
	@SerializedName("size")
	public long size;

	/** The width in pixels, or null if the file is not an image. */
	@SerializedName("width")
	public Integer width;

	/** The height in pixels, or null if the file is not an image. */
	@SerializedName("height")
	public Integer height;

	/** The date and time when the file was added, for example "2026-10-20 09:30:00". */
	@SerializedName("created_at")
	public String createdAt;
}
