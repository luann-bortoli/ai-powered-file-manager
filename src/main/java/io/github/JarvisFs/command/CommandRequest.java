package io.github.JarvisFs.command;

public class CommandRequest {

    private ActionType action;
    private String name;
    private String newName;
    private String content;
    private String message;

    public ActionType getAction()
    {
        return action;
    }

    public void setAction(ActionType action)
    {
        this.action = action;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNewName()
    {
        return newName;
    }

    public void setNewName(String newName)
    {
        this.newName = newName;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
