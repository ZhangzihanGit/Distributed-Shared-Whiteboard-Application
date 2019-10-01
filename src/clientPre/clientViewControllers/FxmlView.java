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
    };

    abstract String getTitle();
    abstract String getFxmlFile();
}
