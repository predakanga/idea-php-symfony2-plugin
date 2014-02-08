package fr.adrienbrault.idea.symfony2plugin.widget.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import fr.adrienbrault.idea.symfony2plugin.Symfony2Icons;
import fr.adrienbrault.idea.symfony2plugin.TwigHelper;
import fr.adrienbrault.idea.symfony2plugin.routing.RouteHelper;
import fr.adrienbrault.idea.symfony2plugin.templating.util.TwigUtil;
import fr.adrienbrault.idea.symfony2plugin.toolwindow.Symfony2SearchForm;
import icons.TwigIcons;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SymfonyProfilerWidgetActions {

    public static class TemplateAction extends AnAction {

        private String templateName;
        private Project project;

        public TemplateAction(Project project, @Nullable String text) {
            super(TwigUtil.getFoldingTemplateNameOrCurrent(text), "Open Template", TwigIcons.TwigFileIcon);
            this.templateName = text;
            this.project = project;
        }

        @Override
        public void actionPerformed(AnActionEvent e) {

            List<PsiFile> psiFiles = Arrays.asList(TwigHelper.getTemplateFilesByName(project, templateName));

            // @TODO: multiple targets?
            if(psiFiles.size() > 0) {
                Symfony2SearchForm.navigateToPsiElement(psiFiles.get(0));
            }

        }
    }

    public static class RouteAction extends AnAction {

        private String routeName;
        private Project project;

        public RouteAction(Project project, @Nullable String text) {
            super(text, "Open Route", Symfony2Icons.ROUTE);
            this.routeName = text;
            this.project = project;
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            PsiElement[] targets = RouteHelper.getMethods(project, this.routeName);
            if(targets.length > 0) {
                Symfony2SearchForm.navigateToPsiElement(targets[0]);
            }
        }

    }

    public static class MethodAction extends AnAction {

        private String methodShortcut;
        private Project project;

        public MethodAction(Project project, @Nullable String text) {
            super(text, "Open Method", com.jetbrains.php.PhpIcons.METHOD);
            this.methodShortcut = text;
            this.project = project;
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            PsiElement[] method = RouteHelper.getMethodsOnControllerShortcut(project, this.methodShortcut);
            if(method.length > 0) {
                Symfony2SearchForm.navigateToPsiElement(method[0]);
            }
        }
    }

}

