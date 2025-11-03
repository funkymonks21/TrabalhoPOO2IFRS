package com.exercicioJPAFuncionario;

import java.util.Calendar;
import com.exercicioJPAFuncionario.controller.Funcionario;
import com.exercicioJPAFuncionario.service.FuncionarioService;

public class MainApp {	
    public static void main(String[] args) {
        FuncionarioService service = new FuncionarioService();
        Funcionario novoFuncionario = new Funcionario();
        Funcionario novoFuncionario2 = new Funcionario();
        novoFuncionario.setEmail("carlosalbertodenobrega@gmail.com");
        novoFuncionario.setNome("Carlos Alberto de Nobrega");
        service.cadastrarFuncionario(novoFuncionario);
        System.out.println("Funcionario cadastrado com sucesso!");
 
        novoFuncionario2.setEmail("ericklenscher@gmail.com");
        novoFuncionario2.setNome("Erick Lenscher");
        service.cadastrarFuncionario(novoFuncionario2);
        System.out.println("Funcionario cadastrado com sucesso!"); 
        
        System.out.println("-----Encontra funcionario----------");
        for (Funcionario f : service.listarFuncionario()) {
        	System.out.println(f.toString());
        }
        
        System.out.println("-----Listar funcionario por id----------"+novoFuncionario2.getId());
        Funcionario f = service.listarFuncionario(novoFuncionario2.getId());
        System.out.println(f);
        System.out.println("-----Atualizar funcionario por id----------");
        f = service.listarFuncionario(novoFuncionario2.getId());
        
        Calendar hoje = Calendar.getInstance();
        hoje.set(Calendar.HOUR_OF_DAY, 0);
        f.setData_contratacao(hoje);
        f.setNome("Thiago");
        service.atualizarFuncionario(f);
        System.out.println("-----Funcionario excluido----------");
        System.out.println(f.toString());        
        service.excluirFuncionario(f);
        System.out.println("-----Listar funcionarios----------");
        for (Funcionario ff : service.listarFuncionario()) {
        	System.out.println(ff.toString());
        }                  
      }
}
