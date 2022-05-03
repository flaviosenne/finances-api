package com.project.finances.domain.exception.messages;

public class MessagesException {
    public static final String INVALID_CREDENTIALS = "Credenciais inválidas";
    public static final String USER_NOT_FOUND = "Usuário não encontrado";
    public static final String USER_REQUEST_INVITE_NOT_FOUND = "Solicitante do convite não existe";
    public static final String USER_ALREADY_PUBLIC = "Usuário já está com a visibilidade para o publico";
    public static final String CONTACT_NOT_FOUND = "Username não encontrado / visibilidade privada";
    public static final String INVITE_NOT_FOUND = "Convite não encontrado";
    public static final String EMAIL_ALREADY_EXISTS = "Email já cadastrado na base de dados";
    public static final String INVALID_CODE_USER = "Código do usuário inválido";
    public static final String USER_CANT_BE_THE_SAME = "Você não pode mandar convite para si mesmo";
    public static final String INVITE_ALREADY_EXISTS = "Você já tem uma solicitação com esse usuário";


    public static final String CATEGORY_NOT_FOUND = "Categoria não encontrada";
    public static final String CATEGORY_USER_NOT_PROVIDER = "Usuário não informado para categoria";


    public static final String CASH_FLOW_CATEGORY_NOT_PROVIDER = "Categoria não exitente para esse usuario";
    public static final String CASH_FLOW_NOT_FOUND = "Lançamento não encontrado";



}
