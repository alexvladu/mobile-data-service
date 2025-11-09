package org.acme.Multiparts;


import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

public class MultiPartAvatar {
    @FormParam("avatar")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public FileUpload avatar;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;
}
