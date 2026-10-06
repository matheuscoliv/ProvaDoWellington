package app;

import dao.CategoriaRepositoryJdbc;
import model.Categoria;
import model.Produto;

import java.util.List;
import java.util.Optional;

public class TesteCategoria {
    public static void main(String[] args) {
        CategoriaRepositoryJdbc repository = new CategoriaRepositoryJdbc();

        try {
            Categoria categoria = new Categoria();
            categoria.setNome("Games");
            categoria.setDescricao("Produtos para jogos");

            repository.inserir(categoria);
            System.out.println("Categoria inserida: " + categoria.getIdCategoria());

            Optional<Categoria> resultado = repository.buscarPorId(categoria.getIdCategoria());
            if (resultado.isPresent()) {
                Categoria encontrada = resultado.get();
                System.out.println("Categoria encontrada: " + encontrada.getNome());
            }

            List<Produto> produtos = repository.listarProdutosPorCategoria(categoria.getIdCategoria());
            System.out.println("Produtos:");
            for (Produto produto : produtos) {
                System.out.println(produto.getIdProduto() + " - " + produto.getNome() + " - " + produto.getPreco());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}