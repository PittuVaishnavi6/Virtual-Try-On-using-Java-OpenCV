# Virtual Try-On using Java & OpenCV

A fun and interactive desktop application that lets you try on virtual glasses in real-time using your webcam! Built using Java, OpenCV, and Swing GUI.

---

## Features

- Real-time face detection using Haar cascades
- Change between 4 different virtual glasses by pressing keys `1` to `4`
- Live webcam feed using OpenCV’s `VideoCapture`
- Transparent PNG glasses overlay support
- No more blue tint issue — accurate colors maintained

---


##  Technologies Used

- Java (JDK 8 or above)
- OpenCV 4.1.1
- Swing (Java GUI)
- Haar Cascade Classifier for face detection
- Maven (optional, for dependency management)

---

##  Project Structure

```
VirtualTryOnJava/
│
├── lib/
│   ├── opencv_java4110.dll               # OpenCV DLL file
│   └── opencv-4110.jar                   # OpenCV Java JAR
│
├── src/
│   └── main/
│       ├── java/
│       │   └── tryon/
│       │       └── VirtualTryOn.java     # Main Java class
│       │
│       └── resources/
│           ├── models/
│           │   ├── haarcascade_frontalface_alt.xml   # Face detection model
│           │   └── haarcascade_eye.xml               # Optional eye model
│           │
│           └── glasses/
│               ├── glasses_01.png
│               ├── glasses_02.png
│               ├── glasses_03.png
│               └── glasses_04.png
│
├── pom.xml                               # Maven configuration file
└── README.md                             # Project documentation
 
---

##  How to Run the Project

Follow these steps to successfully run the Virtual Glasses Try-On project on your local machine:

---

### 1. **Clone the Repository**
```bash
git clone https://github.com/your-username/VirtualTryOnJava.git
cd VirtualTryOnJava
```

---

### 2. **Install Java and OpenCV**

- Make sure **Java (JDK 17 or above)** is installed.
- Download **OpenCV for Windows** from the official site: [https://opencv.org/releases/](https://opencv.org/releases/)
- Extract the downloaded ZIP.
- Inside the extracted folder, navigate to:
  ```
  opencv/build/java/x64/opencv_java4110.dll
  ```
- Copy the file `opencv_java4110.dll` and place it in your project’s
  and also copy the file 'opencv-4110.jar' 
  ```
  VirtualTryOnJava/lib/
  ```

---

### 3. **Update DLL Load Path in Code**

In `src/main/java/tryon/VirtualTryOn.java`, update this line:

```java
System.load("C:\\Users\\YourName\\path\\to\\opencv_java4110.dll");
```

✅ Replace the path above with the **absolute path** to your actual `.dll` file.  
Example:
```java
System.load("C:\\Users\\Vaishnavi\\Downloads\\VirtualTryOnJava\\lib\\opencv_java4110.dll");
```

---

### 4. **Run the Project**

#### ✅ Option A: Using an IDE (like IntelliJ or VS Code)
- Open the project.
- Right-click `VirtualTryOn.java` and run it.

#### ✅ Option B: Using Command Line (VS Code Terminal or PowerShell)
Run the following commands:

```bash
mvn clean install
mvn compile exec:java "-Dexec.mainClass=tryon.VirtualTryOn" "-Dexec.jvmArgs=-Djava.library.path=lib"
```

---

Now your webcam will open and detect your face with virtual glasses overlaid!

Press **keys 1–4** to switch between glasses styles in real-time.

---



