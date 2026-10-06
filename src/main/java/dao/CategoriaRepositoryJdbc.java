package dao;

import model.Categoria;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaRepositoryJdbc {

    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstancia().getConnection();
    }

    public void inserir(Categoria categoria) throws SQLException {

        String sql = """
                INSERT INTO categoria (nome, descricao)
                VALUES (?, ?)
                """;

        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {

                if (rs.next()) {
                    categoria.setIdCategoria(rs.getInt(1));
                }
            }
        }
    }

    public Optional<Categoria> buscarPorId(int id) throws SQLException {

        String sql = """
                SELECT id_categoria, nome, descricao
                FROM categoria
                WHERE id_categoria = ?
                """;

        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Categoria categoria = new Categoria();

                    categoria.setIdCategoria(
                            rs.getInt("id_categoria")
                    );

                    categoria.setNome(
                            rs.getString("nome")
                    );

                    categoria.setDescricao(
                            rs.getString("descricao")
                    );

                    return Optional.of(categoria);
                }
            }
        }

        return Optional.empty();
    }

    public List<Produto> listarProdutosPorCategoria(
            int idCategoria) throws SQLException {

        String sql = """
                SELECT
                    p.id_produto,
                    p.nome,
                    p.preco,
                    p.estoque
                FROM produto p
                INNER JOIN categoria c
                    ON p.id_categoria = c.id_categoria
                WHERE c.id_categoria = ?
                """;

        List<Produto> produtos = new ArrayList<>();

        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, idCategoria);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Produto produto = new Produto();

                    produto.setIdProduto(
                            rs.getInt("id_produto")
                    );

                    produto.setNome(
                            rs.getString("nome")
                    );

                    produto.setPreco(
                            rs.getBigDecimal("preco")
                    );

                    produto.setEstoque(
                            rs.getInt("estoque")
                    );

                    produtos.add(produto);
                }
            }
        }

        return produtos;
    }
}