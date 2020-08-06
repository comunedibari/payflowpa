package it.tasgroup.idp.sftp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.ChannelSftp.LsEntrySelector;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SftpClient {
	
	private Log logger = LogFactory.getLog(this.getClass());

	private String user, host;
	private int port;
	private String password, privateKey, privateKeyPassphrase;

	private Session session = null;
	private ChannelSftp channelSftp = null;

	public SftpClient(String user, String host, int port) {
		super();
		this.user = user;
		this.host = host;
		this.port = port;
	}

	public SftpClient(String user, String host, int port, String password, String privateKey, String privateKeyPassphrase) {
		super();
		this.user = user;
		this.host = host;
		this.port = port;
		this.password = password;
		this.privateKey = privateKey;
		this.privateKeyPassphrase = privateKeyPassphrase;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIdentity(String privateKey, String privateKeyPassphrase) {
		this.privateKey = privateKey;
		this.privateKeyPassphrase = privateKeyPassphrase;
	}

	public void connect() throws SftpClientException {
		logger.info("Connessione all'host " + this.host + ":" + this.port);
		try {
			
			logger.info("Creazione sessione");
			JSch jsch = new JSch();
			session = jsch.getSession(this.user, this.host, this.port);
			if (this.password != null && !this.password.trim().isEmpty()) {
				logger.debug("uso la password");
				session.setPassword(this.password);
			} else {
				logger.debug("uso privateKey/passphrase");
				jsch.addIdentity(privateKey, privateKeyPassphrase);
			}
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			logger.info("Sessione connessa all'host " + this.host);


			logger.info("Apertura canale SFTP");
			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			logger.info("Canale SFTP connesso");
			
			logger.info("Working Directory remota: " + channelSftp.pwd());
			logger.info("Working Directory locale: " + channelSftp.lpwd());

		} catch (Exception e) {
			disconnect();
			throw new SftpClientException("Errore durante la connessione", e);
		}
	}

	public void disconnect() {
		logger.info("Disconnessione dall'host " + this.host);
		if (channelSftp != null) {
			channelSftp.disconnect();
			logger.debug("Canale SFTP disconnesso");
			channelSftp = null;
		}
		if (session != null) {
			session.disconnect();
			logger.debug("Sessione disconnessa");
			session = null;
		}
	}

	private void check() throws SftpClientException {
		if (channelSftp == null) {
			throw new SftpClientException("ChannelSftp non inizializzato. Utilizzare il metodo connect()");
		}
	}


	public List<String> ls() throws SftpClientException {
		check();
		try {
			String pwd = channelSftp.pwd();
			logger.debug("Lista dei file in: " + pwd);

			final List<String> fileNameList = new ArrayList<String>();
			channelSftp.ls(pwd, new LsEntrySelector() {
				@Override
				public int select(LsEntry entry) {
					if (!entry.getAttrs().isDir()) {
						fileNameList.add(entry.getFilename());
					}
					return LsEntrySelector.CONTINUE;
				}
			});
			return fileNameList;
		} catch (Exception e) {
			throw new SftpClientException(e);
		}
	}

	public List<String> lls() throws SftpClientException {
		check();
		try {
			String lpwd = channelSftp.lpwd();
			logger.debug("Lista dei file in: " + lpwd);

			File folder = new File(lpwd);
			File[] found = folder.listFiles();

			final List<String> fileNameList = new ArrayList<String>();
			for (File file : found) {
			  if (file.isFile()) {
				fileNameList.add(file.getName());
			  }
			}
			return fileNameList;
		} catch (Exception e) {
			throw new SftpClientException(e);
		}
	}
	
	public void cd(String remotePath) throws SftpClientException {
		check();
		try {
			channelSftp.cd(remotePath);
			logger.debug("Working Directory remota: " + channelSftp.pwd());
		} catch (Exception e) {
			throw new SftpClientException(e);
		}
	}

	public void lcd(String localPath) throws SftpClientException {
		check();
		try {
			channelSftp.lcd(localPath);
			logger.debug("Working Directory locale: " + channelSftp.lpwd());
		} catch (Exception e) {
			throw new SftpClientException(e);
		}
	}

	public void get(String src, String dst) throws SftpClientException {
		check();
		try {
			channelSftp.get(src, dst);
		} catch (Exception e) {
			throw new SftpClientException(e);
		}
	}

	public void rm(String remotePath) throws SftpClientException {
		check();
		try {
			channelSftp.rm(remotePath);
		} catch (Exception e) {
			throw new SftpClientException(e);
		}
	}

}
