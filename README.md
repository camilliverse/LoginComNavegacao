# Controle de Doação de Sangue

Aplicativo Android simples para controle de doações de sangue.

## Descrição

Aplicativo desenvolvido em Java para Android Studio que permite o registro e acompanhamento de doações de sangue. Utiliza SharedPreferences para persistência de dados local, sem necessidade de banco de dados externo.

## Funcionalidades

- Login de usuários cadastrados
- Cadastro de novos usuários com tipo sanguíneo
- Registro de doações (data, local, quantidade)
- Histórico de doações realizadas
- Interface simples e intuitiva

## Tecnologias

- **Linguagem**: Java
- **IDE**: Android Studio
- **Persistência**: SharedPreferences
- **JSON**: Gson
- **Lista**: RecyclerView

## Estrutura do Projeto

```
app/src/main/java/br/edu/unifaj/cc/mobile/logincomnavegacao/
├── model/
│   ├── User.java          # Modelo do usuário
│   └── Doacao.java      # Modelo da doação
├── util/
│   └── PrefsManager.java # Gerenciador de SharedPreferences
├── adapter/
│   └── DoacaoAdapter.java # Adapter do RecyclerView
├── LoginActivity.java    # Tela de login
├── CadastroActivity.java # Tela de cadastro
├── HomeActivity.java     # Tela principal
├── DoacaoActivity.java  # Registro de doações
└── HistoricoActivity.java # Histórico de doações
```

## Fluxo das Telas

1. **Login** → Usuário acessa com email e senha
2. **Cadastro** → Novo usuário se registra
3. **Home** → Menu principal após login
4. **Doação** → Registra nova doação
5. **Histórico** → Lista de doações realizadas

## Como Executar

1. Clone o repositório
2. Abra no Android Studio
3. Aguarde a sincronização do Gradle
4. Execute no emulador ou dispositivo

### Gerar APK

1. No menu superior, vá em: **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. Aguarde a compilação
3. O APK será gerado em: `app/build/outputs/apk/debug/app-debug.apk`

### Via Linha de Comando

```bash
./gradlew assembleDebug
```

## Dados Armazenados

Os dados são armazenados localmente no dispositivo usando SharedPreferences no formato JSON:

### Usuário (SharedPreferences key: "user")
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456",
  "tipoSanguineo": "O+"
}
```

### Lista de Doações (SharedPreferences key: "doacoes")
```json
[
  {
    "data": "13/04/2026",
    "local": "Hospital Central",
    "quantidade": "450ml"
  },
  {
    "data": "01/03/2026",
    "local": "Hemocentro Municipal",
    "quantidade": "450ml"
  }
]
```

## Regras de Negócio

- Campos vazios não são permitidos em nenhuma tela
- Login validar email e senha salvos
- Apenas usuários logados podem registrar doações
- Histórico exibe todas as doações do usuário

## Descrição das Classes

### Model

- **User.java**: Classe modelo com atributos nome, email, senha e tipoSanguineo
- **Doacao.java**: Classe modelo com atributos data, local e quantidade

### Util

- **PrefsManager.java**: Classe auxiliar para gerenciar dados no SharedPreferences, com métodos para salvar/recuperar usuário, fazer login, salvar/listar doações

### Adapter

- **DoacaoAdapter.java**: Adapter para o RecyclerView que exibe a lista de doações

### Activities

- **LoginActivity.java**: Tela inicial com validação de email e senha
- **CadastroActivity.java**: Tela para cadastrar novos usuários
- **HomeActivity.java**: Tela principal com menu de opções
- **DoacaoActivity.java**: Tela para registrar novas doações
- **HistoricoActivity.java**: Tela com lista de doações usando RecyclerView

## Manual de Usuário

### Primeiro Acesso

1. Na tela de login, clique em "Ir para cadastro"
2. Preencha todos os campos (nome, email, senha, tipo sanguíneo)
3. Clique em "Cadastrar"
4. Você será redirecionado para a tela de login
5. Faça login com os dados cadastrados

### Registrar Doação

1. Na tela principal (Home), clique em "Registrar Doação"
2. Preencha a data, local e quantidade
3. Clique em "Salvar"
4. A doação será armazenada no histórico

### Ver Histórico

1. Na tela principal (Home), clique em "Ver Histórico"
2. Veja a lista de todas as doações realizadas
3. Cada item mostra: data, local e quantidade

### Sair

1. Na tela principal (Home), clique em "Sair"
2. Você será redirecionado para a tela de login

## Autores

- Rodolfo Rodrigues Pinheiro - RA: 12530689
- Rodrigo Pereira Junior - RA: 12529249
- Camilli dos Santos - RA: 12529495
- Otávio Siqueira Gonçalves - RA: 12529937
- Gabriel Rodrigues de Oliveira - RA: 12529520

## Diagramas de Classe

Os diagramas de classes do projeto estão disponíveis na pasta `diagramas/`:

### Arquivos Disponíveis

| Arquivo | Descrição |
|--------|-----------|
| `diagramas/classes-dominio.plantuml` | Diagrama do modelo de domínio (PlantUML) |
| `diagramas/diagrama-doacaoSangue.png` | Diagrama visual em PNG |

![Diagrama](diagramas/diagrama-doacaoSangue.png)

### Estrutura do Modelo de Domínio

```
model/
├── enums/
│   ├── TipoSanguineo (A, B, AB, O)
│   ├── FatorRh (POSITIVO, NEGATIVO)
│   ├── StatusAgendamento (PENDENTE, CONFIRMADO, CANCELADO, REALIZADO)
│   └── StatusBolsa (DISPONIVEL, RESERVADA, UTILIZADA, VENCIDA)
├── entity/
│   ├── Hemocentro
│   ├── Endereco
│   ├── Agendamento
│   └── BolsaSangue
└── user/
    ├── User
    └── Doador
```

## Licença

MIT