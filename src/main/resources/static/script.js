const API_URL = '/api/produtos';

const form = document.getElementById('produtoForm');
const tabelaBody = document.querySelector('#tabelaProdutos tbody');
const btnSalvar = document.getElementById('btn-salvar');
const btnCancelar = document.getElementById('btn-cancelar');
const formTitle = document.getElementById('form-title');

// Carregar produtos ao iniciar
document.addEventListener('DOMContentLoaded', listarProdutos);

// Manipular envio do formulário
form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('produtoId').value;
    const produto = {
        nome: document.getElementById('nome').value,
        modelo: document.getElementById('modelo').value,
        preco: parseFloat(document.getElementById('preco').value),
        descricao: document.getElementById('descricao').value
    };

    try {
        let response;
        if (id) {
            // Editar
            response = await fetch(`${API_URL}/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(produto)
            });
        } else {
            // Cadastrar
            response = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(produto)
            });
        }

        if (response.ok) {
            limparFormulario();
            listarProdutos();
            alert(id ? 'Produto atualizado com sucesso!' : 'Produto cadastrado com sucesso!');
        } else {
            alert('Erro ao salvar produto.');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro de conexão.');
    }
});

// Botão Cancelar
btnCancelar.addEventListener('click', limparFormulario);

// Função para listar produtos
async function listarProdutos() {
    try {
        const response = await fetch(API_URL);
        const produtos = await response.json();

        tabelaBody.innerHTML = '';

        produtos.forEach(produto => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${produto.id}</td>
                <td>${produto.nome}</td>
                <td>${produto.modelo || '-'}</td>
                <td>R$ ${produto.preco.toFixed(2)}</td>
                <td>
                    <button class="btn btn-edit" onclick="prepararEdicao(${produto.id})">Editar</button>
                    <button class="btn btn-danger" onclick="removerProduto(${produto.id})">Excluir</button>
                </td>
            `;
            tabelaBody.appendChild(tr);
        });
    } catch (error) {
        console.error('Erro ao listar produtos:', error);
    }
}

// Função para preparar edição (global para ser acessível via onclick)
window.prepararEdicao = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (response.ok) {
            const produto = await response.json();

            document.getElementById('produtoId').value = produto.id;
            document.getElementById('nome').value = produto.nome;
            document.getElementById('modelo').value = produto.modelo || '';
            document.getElementById('preco').value = produto.preco;
            document.getElementById('descricao').value = produto.descricao || '';

            formTitle.textContent = 'Editar Produto';
            btnSalvar.textContent = 'Atualizar';
            btnCancelar.style.display = 'inline-block';

            // Scroll para o formulário
            document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
        }
    } catch (error) {
        console.error('Erro ao buscar produto:', error);
    }
};

// Função para remover produto (global)
window.removerProduto = async (id) => {
    if (confirm('Tem certeza que deseja excluir este produto?')) {
        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                listarProdutos();
                alert('Produto excluído com sucesso!');
            } else {
                alert('Erro ao excluir produto.');
            }
        } catch (error) {
            console.error('Erro:', error);
        }
    }
};

function limparFormulario() {
    form.reset();
    document.getElementById('produtoId').value = '';
    formTitle.textContent = 'Novo Produto';
    btnSalvar.textContent = 'Cadastrar';
    btnCancelar.style.display = 'none';
}
