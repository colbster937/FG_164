package net.minecraftforge.gradle.common;

import java.util.LinkedList;

import org.gradle.api.Project;

public class BaseExtension
{
    protected Project project;
    protected String version = "null";
    protected String mcpVersion = "unknown";
    protected String clientHash = "null";
    protected String serverHash = "null";
    protected String assetDir = "eclipse/assets";
    private LinkedList<String> srgExtra = new LinkedList<String>();

    public BaseExtension(Project project)
    {
        this.project = project;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getMcpVersion()
    {
        return mcpVersion;
    }

    public void setMcpVersion(String mcpVersion)
    {
        this.mcpVersion = mcpVersion;
    }

    public String getClientHash()
    {
        return clientHash;
    }

    public void setClientHash(String clientHash)
    {
        this.clientHash = clientHash;
    }

    public String getServerHash()
    {
        return serverHash;
    }

    public void setServerHash(String serverHash)
    {
        this.serverHash = serverHash;
    }

    public void setAssetDir(String value)
    {
        this.assetDir = value;
    }

    public String getAssetDir()
    {
        return this.assetDir;
    }

    public LinkedList<String> getSrgExtra()
    {
        return srgExtra;
    }
    
    public void srgExtra(String in)
    {
        srgExtra.add(in);
    }
}