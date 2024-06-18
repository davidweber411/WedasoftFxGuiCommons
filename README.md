# Description

A library which allows you to handle all kind of scenes and dialogs the easy way.

##### Dialog API

- Create complex .fxml file based dialogs and pass and retrieve arguments without any effort.


- Profit from predefined common information-, warning-, error-, confirmation- and input dialogs.

##### Scene API

- Switch the content of a scene easily. Pass and retrieve arguments if wanted.

# Requirements

This Java library was built by using JDK 17.<br>
Please make sure that your project is using at least JDK 17 too.

# Dependencies to add

##### Maven

    <!-- Maven looks in the central repository by default. -->
    <dependency>
      <groupId>com.wedasoft</groupId>
      <artifactId>wedasoftfxguicommons</artifactId>
      <version>1.0.0</version>
    </dependency>

##### Gradle

    repositories {
      mavenCentral()
    }
    dependencies {
      implementation("com.wedasoft:wedasoftfxguicommons:1.0.0")
    }

# Common Dialogs

    JfxDialogUtil.createInformationDialog(String message).showAndWait();

    JfxDialogUtil.createInformationDialog(String message, String messageHeader).showAndWait();

    JfxDialogUtil.createWarningDialog(String message).showAndWait();
    
    JfxDialogUtil.createWarningDialog(String message, String messageHeader).showAndWait();

    JfxDialogUtil.createErrorDialog(String message).showAndWait();
     
    JfxDialogUtil.createErrorDialog(String message, Exception exceptionForStacktrace).showAndWait();

    String inputtedText = JfxDialogUtil.displayInputDialogAndGetResult(String dialogText);

    boolean confirmed = JfxDialogUtil.displayConfirmDialogAndGetResult(String headerText, String contentText);
    
    JfxDialogUtil.displayCloseStageDialog(Stage stageToClose);
    
    JfxDialogUtil.displayExitProgramDialog();

# Complex Dialogs

Your dream is to create a fxml based dialog, pass arguments into it, and compute them in the controller of the new
Scene? Then take a look at this:

    JfxDialogUtil.createAndShowFxmlDialog(
        /* title */                  "My Dialog title",
        /* dialogIsModal */          true,
        /* dialogIsResizeable */     false,
        /* absoluteFxmlFileUrl */    getClass().getResource("/com/example/app/views/scene1.fxml"),
        /* preferred sceneSize */    new Dimension2D(600, 500),
        /* initMethodOfController */ (Consumer<Scene1Controller>) consumer -> consumer.init("myparamter1"),
        /* callbackOnDialogClose */  () -> integerValueChangedByCallback = 52)

# SceneUtil API

Switch scenes of stages easily. Pass and retrieve arguments to your scenes and compute them.

### Step 1 (optional): Create an init() in the new controller ...

    public void init(String passedParameter){
        // compute the passed parameters
        // do other "constructor things"
    }

### Step 2: Determine the stage ...

    SceneUtil.getStageByActionEvent(ActionEvent event);

    SceneUtil.getStageByChildNode(Node node);

    SceneUtil.getStageByScene(Scene scene);

### Step 3: Switch the root content of its scene ...

    SceneUtil.switchSceneRoot(
        SceneUtil.getStageByActionEvent(event),
        getClass().getResource("/com/example/app/views/scene1.fxml"),
        null / (Consumer<Scene1Controller>) controller -> controller.init("PassThisToScene1"));

