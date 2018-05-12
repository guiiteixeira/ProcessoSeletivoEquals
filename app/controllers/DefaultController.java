package controllers;

import play.i18n.Messages;
import play.mvc.Controller;

public class DefaultController extends Controller {

	protected static void renderErrorCPF(String retorno) {
    	response.status = 500;
    	renderText(Messages.get("mensagem.erroCpfExistente"));
    }

}
