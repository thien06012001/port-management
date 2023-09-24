package views;

import views.welcomePageView.WelcomePageView;

public class MainView {
    public void displayWindow() {
        WelcomePageView welcomePageView = new WelcomePageView();
        welcomePageView.displayWelcomePage();
    }
}
