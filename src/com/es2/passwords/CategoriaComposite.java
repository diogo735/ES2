package com.es2.passwords;

import java.util.*;

public class CategoriaComposite implements Categoria{
    private final String nome;
    private final List<Categoria> filhos = new ArrayList<>();

    public CategoriaComposite(String nome) {
        this.nome = nome;
    }

    public void adicionar(Categoria componente) {
        filhos.add(componente);
    }

    public String getNome() {
        return nome;
    }

    public List<Categoria> getFilhos() {
        return filhos;
    }

    public CategoriaComposite encontrarOuCriarSubcategoria(String nome) {
        for (Categoria filho : filhos) {
            if (filho instanceof CategoriaComposite) {
                CategoriaComposite comp = (CategoriaComposite) filho;
                if (comp.getNome().equalsIgnoreCase(nome)) {
                    return comp;
                }
            }
        }
        CategoriaComposite nova = new CategoriaComposite(nome);
        adicionar(nova);
        return nova;
    }
    @Override
    public void exibir(String prefixo) {
        exibir(prefixo, new HashMap<>());  // Chama o novo método com mapa vazio
    }
    public void exibir(String prefixo, Map<String, List<String>> senhaInfo) {
        String atual = prefixo.isEmpty() ? nome : prefixo + " → " + nome;
        System.out.println(atual);
        if (senhaInfo.containsKey(nome)) {
            for (String info : senhaInfo.get(nome)) {
                System.out.println(" -> " + info);
            }
        }
        for (Categoria filho : filhos) {
            filho.exibir(atual, senhaInfo);
        }
    }

    public void remover(String site) {
        for (Iterator<Categoria> iterator = filhos.iterator(); iterator.hasNext(); ) {
            Categoria filho = iterator.next();
            if (filho instanceof Pass_tipo_categoria) {
                Pass_tipo_categoria pass = (Pass_tipo_categoria) filho;
                if (pass.getSite().equals(site)) {
                    iterator.remove();
                    System.out.println("Removido: " + site + " da categoria: " + getNome());
                    break;
                }
            }
        }
    }




}