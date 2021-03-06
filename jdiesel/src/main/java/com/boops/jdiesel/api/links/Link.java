package com.boops.jdiesel.api.links;

import android.util.Log;

import com.boops.jdiesel.api.DeviceInfo;
import com.boops.jdiesel.api.connectors.Connection;
import com.boops.jdiesel.api.connectors.Connector;
import com.boops.jdiesel.api.sessions.Session;
import com.boops.jdiesel.api.sessions.SessionCollection;
import com.boops.jdiesel.api.transport.Transport;
import com.boops.jdiesel.connection.AbstractLink;
import com.boops.jdiesel.logger.LogMessage;
import com.boops.jdiesel.logger.Logger;


public abstract class Link extends AbstractLink {
	
	protected Connector parameters = null;
	private DeviceInfo device_info;
	
	private Logger logger = null;
	
	public Link(Connector parameters, DeviceInfo device_info) {
		this.parameters = parameters;
		this.device_info = device_info;
		
		this.setSessionCollection(new SessionCollection(this));
	}
	
	public abstract void setStatus(Connector.Status status);
	
	@Override
	protected void createConnection(Transport transport) {
		if(transport.isLive()) {
			this.connection = new Connection(this, this.device_info, transport);
			this.connection.start();
		}
	}
	
	@Override
	public Session getSession(String session_id) {
		return (Session)super.getSession(session_id);
	}

	public void log(int level, String message) {
		if(this.logger != null)
			this.logger.log(level, message);
		else
			Log.i("link", message);
	}
	
	public void log(LogMessage message) {
		if(this.logger != null)
			this.logger.log(message);
		else
			Log.i("link", message.getMessage());
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public Session startSession(String password) {
		if(this.parameters.verifyPassword(password))
			return (Session)this.createSession();
		else
			return null;
	}

}
