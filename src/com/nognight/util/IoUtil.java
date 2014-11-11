package com.nognight.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class IoUtil {
	
	public static void close(ServerSocket ss) {
		if(ss != null) {
			try{
				ss.close();
			} catch(IOException e) {
				// 由于本错添没有处理价值，因此在异常处理代码中不做任何处理
				//最多在日 志中记录一下当前文 件无法关闭
			}
		}
	}

	public static void close(Socket s) {
		if(s != null) {
			try{
				s.close();
			} catch(IOException e) {
				// 由于本错添没有处理价值，因此在异常处理代码中不做任何处理
				//最多在日 志中记录一下当前文 件无法关闭
			}
		}
	}

	public static void close(InputStream is) {
		if(is != null) {
			try{
				is.close();// 关闭文 件流
			} catch(IOException e) {
				// 由于本错添没有处理价值，因此在异常处理代码中不做任何处理
				//最多在日 志中记录一下当前文 件无法关闭
			}
		}
	}

	public static void close(OutputStream os) {
		if(os != null) {
			try{
				os.close();// 关闭文 件流
			} catch(IOException e) {
				// 由于本错添没有处理价值，因此在异常处理代码中不做任何处理
				//最多在日 志中记录一下当前文 件无法关闭
			}
		}
	}

	public static void close(RandomAccessFile file) {
		if(file != null) {
			try{
				file.close();// 关闭文 件流
			} catch(IOException e) {
				// 由于本错添没有处理价值，因此在异常处理代码中不做任何处理
				//最多在日 志中记录一下当前文 件无法关闭
			}
		}
	}

	public static void close(Reader file) {
		if(file != null) {
			try{
				file.close();// 关闭文 件流
			} catch(IOException e) {
				// 由于本错添没有处理价值，因此在异常处理代码中不做任何处理
				//最多在日 志中记录一下当前文 件无法关闭
			}
		}
	}

	public static void close(Writer file) {
		if(file != null) {
			try{
				file.close();// 关闭文 件流
			} catch(IOException e) {
				// 由于本错添没有处理价值，因此在异常处理代码中不做任何处理
				//最多在日 志中记录一下当前文 件无法关闭
			}
		}
	}
}
