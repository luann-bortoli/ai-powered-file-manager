# 🤖 JarvisFS

AI-powered CLI filesystem assistant built with Java, Spring Boot and Ollama.

JarvisFS is an experimental backend project focused on integrating local AI models with direct filesystem interaction through natural language commands.

---
<img width="1280" height="720" alt="0523" src="https://github.com/user-attachments/assets/56e33617-9777-43f6-9fb6-bda685255eca" />

---

# ✨ Features

- AI-powered command interpretation
- Local Ollama integration
- CLI-based interaction
- Files and folder management
- JSON command architecture
- Strongly typed backend structure
- Secure filesystem sandbox
- Recursive delete support
- Spring Boot architecture
- Local-first execution

---

# 🧠 Example

### User input

```text
create a folder named documents
```
### AI response
```text
{
  "action": "CREATE_FOLDER",
  "name": "documents",
  "newName": "",
  "content": "",
  "message": ""
}
```

### Executed action
```text
✔ Folder created successfully
```

# ⚙️ Tech Stack
- Java 17
- Spring Boot
- Ollama
- Jackson
- Maven
