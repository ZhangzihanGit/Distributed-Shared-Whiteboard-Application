package clientPre.clientViewControllers;

public enum FxmlView {

    WELCOME {
        @Override
        String getTitle() {
            return "My WhiteBoard";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientWelcome.fxml";
        }
    }, LOGIN {
        @Override
        String getTitle() {
            return "Login";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientLogin.fxml";
        }
    }, SIGNUP {
        @Override
        String getTitle() {
            return "Sign Up";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientSignup.fxml";
        }
    }, IDENTITY {
        @Override
        String getTitle() {
            return "Choose Your Role";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientIdentity.fxml";
        }
    }, CONFIG {
        @Override
        String getTitle() {
            return "Configuration";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/clientConfig.fxml";
        }
    }, CANVAS {
        @Override
        String getTitle() {
            return "Canvas";
        }

        @Override
        String getFxmlFile() {
            return "../clientViews/WhiteBoard.fxml";
        }
    };

    abstract String getTitle();
    abstract String getFxmlFile();
}
