package nl.t42.openstack.client;

import nl.t42.openstack.command.account.AccountInformationCommand;
import nl.t42.openstack.command.account.AccountMetadataCommand;
import nl.t42.openstack.command.account.ListContainersCommand;
import nl.t42.openstack.command.container.*;
import nl.t42.openstack.command.identity.AuthenticationCommand;
import nl.t42.openstack.command.identity.access.Access;
import nl.t42.openstack.command.object.*;
import nl.t42.openstack.model.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class OpenStackClientImpl implements OpenStackClient {

    private Access access;

    private HttpClient httpClient = new DefaultHttpClient();

    private boolean authenticated = false;

    public void authenticate(String username, String password, String authUrl) throws IOException {

        this.access = null;
        this.authenticated = false;

        this.access = new AuthenticationCommand(httpClient, authUrl, username, password).execute();
        this.authenticated = true;
    }

    public AccountInformation getAccountInformation() throws IOException {
        return new AccountInformationCommand(httpClient, access).execute();
    }

    public void setAccountInformation(Map<String, Object> metadata) throws IOException {
        new AccountMetadataCommand(httpClient, access, metadata).execute();
    }

    public Container[] listContainers() throws IOException {
        return new ListContainersCommand(httpClient, access).execute();
    }

    public void createContainer(Container container) throws IOException {
        new CreateContainerCommand(httpClient, access, container).execute();
    }

    public void deleteContainer(Container container) throws IOException {
        new DeleteContainerCommand(httpClient, access, container).execute();
    }

    public void makeContainerPublic(Container container) throws IOException {
        new ContainerRightsCommand(httpClient, access, container, true).execute();
    }

    public void makeContainerPrivate(Container container) throws IOException {
        new ContainerRightsCommand(httpClient, access, container, false).execute();
    }

    public ContainerInformation getContainerInformation(Container container) throws IOException {
        return new ContainerInformationCommand(httpClient, access, container).execute();
    }

    public void setContainerInformation(Container container, Map<String, Object> metadata) throws IOException {
        new ContainerMetadataCommand(httpClient, access, container, metadata).execute();
    }

    public StoreObject[] listObjects(Container container) throws IOException {
        return new ListObjectsCommand(httpClient, access, container).execute();
    }

    public byte[] downloadObject(Container container, StoreObject object) throws IOException {
        return new DownloadObjectCommand(httpClient, access, container, object).execute();
    }

    public void downloadObject(Container container, StoreObject object, File targetFile) throws IOException {
        new DownloadObjectCommand(httpClient, access, container, object, targetFile).execute();
    }

    public void uploadObject(Container container, StoreObject target, byte[] fileToUpload) throws IOException {
        new UploadObjectCommand(httpClient, access, container, target, fileToUpload).execute();
    }

    public void uploadObject(Container container, StoreObject target, File fileToUpload) throws IOException {
        new UploadObjectCommand(httpClient, access, container, target, fileToUpload).execute();
    }

    public ObjectInformation getObjectInformation(Container container, StoreObject object) throws IOException {
        return new ObjectInformationCommand(httpClient, access, container, object).execute();
    }

    public void setObjectInformation(Container container, StoreObject object, Map<String, Object> metadata) throws IOException {
        new ObjectMetadataCommand(httpClient, access, container, object, metadata).execute();
    }

    public void deleteObject(Container container, StoreObject object) throws IOException {
        new DeleteObjectCommand(httpClient, access, container, object).execute();
    }

    public void copyObject(Container sourceContainer, StoreObject sourceObject, Container targetContainer, StoreObject targetObject) throws IOException {
        new CopyObjectCommand(httpClient, access, sourceContainer, sourceObject, targetContainer, targetObject).execute();
    }

    public boolean isAuthenticated() { return this.authenticated; }
    public void setHttpClient(HttpClient httpClient) { this.httpClient = httpClient; }
}