package application;

import model.dao.ItemDao;
import model.dao.DaoFactory;
import model.dao.TipoItemDao;
import model.entities.Item;
import model.entities.TipoItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class TelaItem extends JFrame {
    private static TelaItem instance;

    JComboBox<String> comboBox;
    TipoItem tipoItem;
    TipoItemDao tipoItemDao = DaoFactory.createTipoItemDao();
    ItemDao itemDao = DaoFactory.createItemDao();

    private JTable tabela;
    private DefaultTableModel model;

    public TelaItem() {
        setTitle("Inventário");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelInventario = new JPanel();
        painelInventario.setLayout(new BorderLayout());

        JPanel painelCabecalho = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelCabecalho.setBackground(Color.BLACK);

        JButton botaoAbrirTipos = new JButton("Tipos");
        botaoAbrirTipos.addActionListener(e -> {
            TelaTipoItem.exibirTela();

        });
        painelCabecalho.add(botaoAbrirTipos);

        JButton botaoCadastrar = new JButton("Cadastrar item");
        botaoCadastrar.addActionListener(e -> {
            TipoItemDao tipoItemDao = DaoFactory.createTipoItemDao();
            List<TipoItem> list = tipoItemDao.findAll();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "É necessário ter ao menos um Tipo de Item cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                TelaCadastroItem.exibirTela();
            }

        });
        painelCabecalho.add(botaoCadastrar);

        JLabel label = new JLabel("Bem-vindo(a) ao Inventário", SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        painelCabecalho.add(label);

        painelInventario.add(painelCabecalho, BorderLayout.NORTH);
        add(painelInventario);

        JPanel painelTabela = new JPanel(new BorderLayout());

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField barraDePesquisa = new JTextField(20);
        painelPesquisa.add(barraDePesquisa);

        JButton botaoPesquisar = new JButton("Pesquisar");
        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String campoFiltro = barraDePesquisa.getText();
                String filtro = (String) comboBox.getSelectedItem();
                if (validarEntrada(campoFiltro, filtro)) {
                    pesquisar(campoFiltro, filtro);
                } else {
                    JOptionPane.showMessageDialog(TelaItem.this, "Entrada inválida. Por favor, digite uma data válida.");
                }
            }
        });

        JButton botaoLimparFiltro = new JButton("Mostrar Todos");
        botaoLimparFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tirarFiltro();
            }
        });

        String[] campos = {"ID", "Descrição", "Tipo", "Marca", "Modelo", "Número de Série", "Potência", "Localização", "Enviado", "Número da Nota Fiscal", "Data de Entrada", "Última Qualificação", "Próxima Qualificação"};
        comboBox = new JComboBox<>(campos);

        painelPesquisa.add(comboBox);
        painelPesquisa.add(botaoPesquisar);
        painelPesquisa.add(botaoLimparFiltro);

        painelTabela.add(painelPesquisa, BorderLayout.NORTH);

        String[] colunas = {"ID", "Descrição", "Tipo", "Marca", "Modelo", "Número de Série", "Potência", "Localização", "Enviado", "Número da Nota Fiscal", "Data de Entrada", "Última qualificação", "Próxima Qualificação"};
        Object[][] data = {};

        model = new DefaultTableModel(data, colunas);
        tabela = new JTable(model);

        JScrollPane barraDeRolagem = new JScrollPane(tabela);
        painelTabela.add(barraDeRolagem, BorderLayout.CENTER);

        painelInventario.add(painelTabela, BorderLayout.CENTER);
        add(painelInventario);

        List<Item> list = itemDao.findAll();
        for (Item item : list) {
            adicionarAoModelo(item);
        }

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    criarMenuContexto(e.getX(), e.getY());
                }
            }
        });
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int linha = e.getFirstRow();
                int coluna = e.getColumn();


                if (coluna == 0){
                    JOptionPane.showMessageDialog(TelaItem.this, "Não é possível alterar valor ID", "Mudança não aceita", JOptionPane.ERROR_MESSAGE);
                    tirarFiltro();
                    return;
                }


                if (linha != -1 && coluna != -1) {

                    Object valorNovo = model.getValueAt(linha, coluna);

                    int opcao = JOptionPane.showConfirmDialog(
                            TelaItem.this,
                            "Tem certeza que deseja alterar este valor?",
                            "Confirmação de Alteração",
                            JOptionPane.YES_NO_OPTION);

                    if (opcao == JOptionPane.YES_OPTION) {
                        int  valorPrimeiraColuna = (int) model.getValueAt(linha, 0);
                        if(Objects.equals(valorNovo.toString(), "")){
                            JOptionPane.showMessageDialog(TelaItem.this, "Insira um valor para alterar registro.", "Mudança não aceita", JOptionPane.WARNING_MESSAGE);
                            tirarFiltro();
                            return;
                        }
                        if(coluna>9){
                            if(!validarEntrada(valorNovo.toString(), "Data de Entrada")){
                                JOptionPane.showMessageDialog(TelaItem.this, "Valor inserido não segue padrão (aaaa-mm-dd).", "Mudança não aceita", JOptionPane.ERROR_MESSAGE);
                                tirarFiltro();
                                return;
                            }
                        }

                        atualizarItem(valorPrimeiraColuna, coluna, valorNovo);

                    } else {
                        tirarFiltro();
                    }
                }
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public void limparTabela() {
        model.setRowCount(0);
    }

    public void tirarFiltro() {
        limparTabela();
        List<Item> list = itemDao.findAll();
        for (Item item : list) {
            adicionarAoModelo(item);
        }
    }

    public void pesquisar(String campoFiltro, String filtro) {
        if (campoFiltro.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite um termo de pesquisa.");
            return;
        }
        if (filtro.equals("ID")) {
            try {
                Integer.parseInt(campoFiltro);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "O valor de busca para ID deve ser um número inteiro.");
                return;
            }
        }
        limparTabela();

        boolean temUm = false;
        boolean contem = false;
        String representacaoString;

        List<Item> listaItem = itemDao.findAll();
        for (Item item : listaItem) {
            representacaoString = item.toString();
            switch (filtro) {
                case "ID" -> contem = representacaoString.toLowerCase().contains("item [id=" + campoFiltro.toLowerCase() + ", des");
                case "Descrição" -> contem = representacaoString.toLowerCase().contains("descricao=" + campoFiltro.toLowerCase() + ",");
                case "Tipo" -> contem = representacaoString.toLowerCase().contains(", tipo=" + campoFiltro.toLowerCase() + "]");
                case "Marca" -> contem = representacaoString.toLowerCase().contains("marca=" + campoFiltro.toLowerCase() + ",");
                case "Modelo" -> contem = representacaoString.toLowerCase().contains("modelo=" + campoFiltro.toLowerCase() + ",");
                case "Número de Série" -> contem = representacaoString.toLowerCase().contains("numeroserie=" + campoFiltro.toLowerCase() + ",");
                case "Potência" -> contem = representacaoString.toLowerCase().contains("potencia=" + campoFiltro.toLowerCase() + ",");
                case "Localização" -> contem = representacaoString.toLowerCase().contains("localizacao=" + campoFiltro.toLowerCase() + ",");
                case "Enviado" -> contem = representacaoString.toLowerCase().contains("enviado=" + campoFiltro.toLowerCase() + ",");
                case "Número da Nota Fiscal" -> contem = representacaoString.toLowerCase().contains("notafiscal=" + campoFiltro.toLowerCase() + ",");
                case "Data de Entrada" -> contem = representacaoString.toLowerCase().contains("dataentrada=" + campoFiltro.toLowerCase() + ",");
                case "Última Qualificação" -> contem = representacaoString.toLowerCase().contains("ultimaqualificacao=" + campoFiltro.toLowerCase() + ",");
                case "Próxima Qualificação" -> contem = representacaoString.toLowerCase().contains("proximaqualificacao=" + campoFiltro.toLowerCase() + ",");
            }

            if (contem) {
                temUm = true;
                adicionarAoModelo(item);
            }
        }
        if (!temUm) {
            JOptionPane.showMessageDialog(this, "Nenhum item encontrado que corresponda a pesquisa.");
            tirarFiltro();
        }
    }

    private void adicionarAoModelo(Item item) {
        tipoItem = tipoItemDao.findById(item.getTipoItem().getId());
        model.addRow(new Object[]{item.getId(), item.getDescricao(), tipoItem.getTipo(), item.getMarca(), item.getModelo(), item.getNumeroSerie(), item.getPotencia(), item.getLocalizacao(), item.getEnviado(), item.getNotaFiscal(), item.getDataEntrada(), item.getUltimaQualificacao(), item.getProximaQualificacao()});
    }

    public static void exibirTela() {
        SwingUtilities.invokeLater(() -> {
            if (instance == null) {
                instance = new TelaItem();
            }
            instance.setExtendedState(JFrame.MAXIMIZED_BOTH);
            instance.setVisible(true);
        });
    }


    private void criarMenuContexto(int x, int y) {
        int[] linhasSelecionadas = tabela.getSelectedRows();

        if (linhasSelecionadas.length > 0) {
            JPopupMenu menuContexto = new JPopupMenu();
            JMenuItem itemExcluir = new JMenuItem("Excluir");
            itemExcluir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int opcao = JOptionPane.showConfirmDialog(
                            TelaItem.this,
                            "Tem certeza que deseja excluir o(s) item(s) selecionado(s)?",
                            "Confirmação de Exclusão",
                            JOptionPane.YES_NO_OPTION);

                    if (opcao == JOptionPane.YES_OPTION) {
                        for (int i = linhasSelecionadas.length - 1; i >= 0; i--) {
                            int linha = linhasSelecionadas[i];
                            int idItem = (int) tabela.getValueAt(linha, 0);
                            excluirItem(idItem);
                        }
                    }
                }
            });
            menuContexto.add(itemExcluir);

            menuContexto.show(tabela, x, y);
        }
    }

    private void excluirItem(int idItem) {
        itemDao.deleteById(idItem);
        tirarFiltro();
    }

    private boolean validarEntrada(String campoFiltro, String filtro) {
        if (filtro.equals("Data de Entrada") || filtro.equals("Última Qualificação") || filtro.equals("Próxima Qualificação")) {
            return campoFiltro.matches("^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
        }
        return true;
    }

    private void atualizarItem(int idItem, int coluna, Object valor) {
        Item item = itemDao.findById(idItem);

        switch (coluna) {
            case 1 -> item.setDescricao(valor.toString());
            case 2 -> {
                Boolean achou = true;
                List<TipoItem> list;
                list = tipoItemDao.findAll();
                for (TipoItem obj : list) {
                    if (valor.toString().equalsIgnoreCase(obj.getTipo())){
                        achou = false;

                        item.setTipoItem(obj);
                    }
                }
                if(achou){
                    tirarFiltro();
                    JOptionPane.showMessageDialog(TelaItem.this, "Não existe tipo inserido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            case 3 -> item.setMarca(valor.toString());
            case 4 -> item.setModelo(valor.toString());
            case 5 -> item.setNumeroSerie(valor.toString());
            case 6 -> item.setPotencia(valor.toString());
            case 7 -> item.setLocalizacao(valor.toString());
            case 8 -> item.setEnviado(valor.toString());
            case 9 -> item.setNotaFiscal(valor.toString());
            // Assuming coluna 9, 10, and 11 are related to dates
            case 10, 11, 12 -> {
                Date data = parseDate(valor.toString());
                if (data != null) {

                    if (coluna == 10) {
                        item.setDataEntrada(data);
                    } else if (coluna == 11) {
                        item.setUltimaQualificacao(data);
                    } else if (coluna == 12) {
                        item.setProximaQualificacao(data);
                    }
                }
            }
        }

        itemDao.update(item);
        tirarFiltro();
        JOptionPane.showMessageDialog(TelaItem.this, "Alteração bem sucedida.", "Erro", JOptionPane.INFORMATION_MESSAGE);

    }
    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            return formato.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}


class TelaCadastroItem extends JFrame {
    private static TelaCadastroItem instance;

    private JTextField descricaoField, marcaField, modeloField, numeroSerieField,
            potenciaField, localizacaoField, enviadoField, dataEntradaField, ultimaQualificacaoField, proximaQualificacaoField,notaFiscalField;
    private JComboBox<String> tipoItemComboBox;

    public TelaCadastroItem() {
        TipoItemDao tipoItemDao =  DaoFactory.createTipoItemDao();
        // Configurações básicas da janela
        setTitle("Cadastro de Item");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layou
        setLayout(new BorderLayout());


        JPanel painelCampos = new JPanel(new GridLayout(12, 2));

        // Adiciona rótulos e campos de texto
        painelCampos.add(new JLabel("Tipo do Item:"));
        List<String> strings = new ArrayList<>();

        List<TipoItem> list;
        list = tipoItemDao.findAll();
        for (TipoItem obj : list) {

            strings.add(obj.getTipo());
        }


        tipoItemComboBox = new JComboBox<>(strings.toArray(new String[0]));
        painelCampos.add(tipoItemComboBox);

        painelCampos.add(new JLabel("Descrição:"));
        descricaoField = new JTextField();
        painelCampos.add(descricaoField);

        painelCampos.add(new JLabel("Marca:"));
        marcaField = new JTextField();
        painelCampos.add(marcaField);

        painelCampos.add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        painelCampos.add(modeloField);

        painelCampos.add(new JLabel("Número de Série:"));
        numeroSerieField = new JTextField();
        painelCampos.add(numeroSerieField);

        painelCampos.add(new JLabel("Potência:"));
        potenciaField = new JTextField();
        painelCampos.add(potenciaField);

        painelCampos.add(new JLabel("Localização:"));
        localizacaoField = new JTextField();
        painelCampos.add(localizacaoField);

        painelCampos.add(new JLabel("Enviado:"));
        enviadoField = new JTextField();
        painelCampos.add(enviadoField);

        painelCampos.add(new JLabel("Número da Nota Fiscal:"));
        notaFiscalField = new JTextField();
        painelCampos.add(notaFiscalField);

        painelCampos.add(new JLabel("Data de Entrada:"));
        dataEntradaField = new JTextField();
        painelCampos.add(dataEntradaField);

        painelCampos.add(new JLabel("Última Qualificação:"));
        ultimaQualificacaoField = new JTextField();
        painelCampos.add(ultimaQualificacaoField);

        painelCampos.add(new JLabel("Próxima Qualificação:"));
        proximaQualificacaoField = new JTextField();
        painelCampos.add(proximaQualificacaoField);

        add(painelCampos, BorderLayout.CENTER);


        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarItem();
            }
        });


        add(cadastrarButton,BorderLayout.SOUTH);
    }

    private void cadastrarItem() {

        TipoItemDao tipoItemDao = DaoFactory.createTipoItemDao();

        String tipoItem = (String) tipoItemComboBox.getSelectedItem();

        String descricao = descricaoField.getText();
        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        String numeroSerie = numeroSerieField.getText();
        String potencia = potenciaField.getText();
        String localizacao = localizacaoField.getText();
        String enviado = enviadoField.getText();
        String notaFiscal = notaFiscalField.getText();
        String dataEntrada = dataEntradaField.getText();
        String ultimaQualificacao = ultimaQualificacaoField.getText();
        String proximaQualificacao = proximaQualificacaoField.getText();

        Date data1 = parseDate(dataEntrada);
        Date data2 = parseDate(ultimaQualificacao);
        Date data3 = parseDate(proximaQualificacao);

        if (descricao.isEmpty() || marca.isEmpty() || modelo.isEmpty() || numeroSerie.isEmpty() ||
                potencia.isEmpty() || localizacao.isEmpty() || enviado.isEmpty() || dataEntrada.isEmpty() ||
                ultimaQualificacao.isEmpty() || proximaQualificacao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!(validarEntrada(dataEntrada) && validarEntrada(ultimaQualificacao) && validarEntrada(proximaQualificacao))){
            JOptionPane.showMessageDialog(this, "Valor(es) inserido(s) em datas não segue(m) padrão (aaaa-mm-dd).", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<TipoItem> list;
        list = tipoItemDao.findAll();
        for (TipoItem obj : list) {
            if(obj.getTipo().equalsIgnoreCase(tipoItem)){
                ItemDao itemDao = DaoFactory.createItemDao();
                Item item = new Item(null, descricao, marca, modelo, numeroSerie, potencia, localizacao, enviado ,notaFiscal, data1, data2, data3, obj);
                itemDao.insert(item);

                JOptionPane.showMessageDialog(this, "Item cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        }


    }
    private boolean validarEntrada(String campoFiltro) {
            return campoFiltro.matches("^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
    }
    public static void exibirTela() {
        SwingUtilities.invokeLater(() -> {
            if (instance == null) {
                instance = new TelaCadastroItem();
            }
            instance.setSize(400, 400);
            instance.setVisible(true);
        });
    }
    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            return formato.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class TelaTipoItem extends JFrame {

    TipoItemDao tipoItemDao = DaoFactory.createTipoItemDao();
    private static TelaTipoItem instance;
    private JPanel panel;
    private JTextField novoTipoTextField;
    private DefaultTableModel tableModel;
    JTable tiposTable;
    private TelaTipoItem() {
        super("Tela Tipo Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 525);

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Layout Flow com espaçamento

        // Campo para cadastrar um novo tipo
        novoTipoTextField = new JTextField(20); // Define um tamanho fixo para o campo de texto
        panel.add(new JLabel("Novo Tipo:"));
        panel.add(novoTipoTextField);

        // Botão para adicionar novo tipo
        JButton adicionarTipoButton = new JButton("Adicionar Tipo");
        adicionarTipoButton.addActionListener(e -> cadastrar(novoTipoTextField.getText()));
        panel.add(adicionarTipoButton);

        // Tabela para exibir tipos existentes
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Tipo");

        tiposTable = new JTable(tableModel);
        panel.add(new JScrollPane(tiposTable));

        atualizar();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tiposTable.getColumnCount(); i++) {
            tiposTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Adiciona o painel à tela
        add(panel);


        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int linha = e.getFirstRow();
                int coluna = e.getColumn();


                if (coluna == 0){
                    JOptionPane.showMessageDialog(TelaTipoItem.this, "Não é possível alterar valor ID", "Mudança não aceita", JOptionPane.ERROR_MESSAGE);
                    atualizar();
                    return;
                }
                if (linha != -1 && coluna != -1) {
                    Object valorNovo = tableModel.getValueAt(linha, coluna);

                    int opcao = JOptionPane.showConfirmDialog(
                            TelaTipoItem.this,
                            "Tem certeza que deseja alterar este valor?",
                            "Confirmação de Alteração",
                            JOptionPane.YES_NO_OPTION);

                    if (opcao == JOptionPane.YES_OPTION) {
                        int valorPrimeiraColuna = (int) tableModel.getValueAt(linha, 0);

                        if (Objects.equals(valorNovo.toString(), "")) {
                            JOptionPane.showMessageDialog(TelaTipoItem.this, "Insira um valor para alterar registro.", "Mudança não aceita", JOptionPane.WARNING_MESSAGE);
                            atualizar();
                            return;
                        }


                        atualizarItem(valorPrimeiraColuna, valorNovo.toString());

                    } else {
                        atualizar();
                    }
                }
            }
        });
        tiposTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    criarMenuContexto(e.getX(), e.getY());
                }
            }
        });

    }

    public void excluirTipoItem(int id){
        ItemDao itemDao = DaoFactory.createItemDao();

        List<Item> list = itemDao.findAll();
        for (Item obj : list) {
            if (id == obj.getTipoItem().getId()){
                JOptionPane.showMessageDialog(TelaTipoItem.this, "Há ocorrências deste Tipo em Itens", "Erro!", JOptionPane.ERROR_MESSAGE);

                return;
            }
        }

        tipoItemDao.deleteById(id);
        atualizar();
    }
    public void criarMenuContexto(int x, int y) {
        int[] linhasSelecionadas = tiposTable.getSelectedRows();

        if (linhasSelecionadas.length == 1) {  // Garante que apenas uma linha está selecionada
            JPopupMenu menuContexto = new JPopupMenu();
            JMenuItem itemExcluir = new JMenuItem("Excluir");
            itemExcluir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int opcao = JOptionPane.showConfirmDialog(
                            TelaTipoItem.this,
                            "Tem certeza que deseja excluir o tipo selecionado?",
                            "Confirmação de Exclusão",
                            JOptionPane.YES_NO_OPTION);

                    if (opcao == JOptionPane.YES_OPTION) {
                        int linha = linhasSelecionadas[0];  // Obtém o índice da linha selecionada
                        int id = (int) tiposTable.getValueAt(linha, 0);
                        excluirTipoItem(id);
                    }
                }
            });
            menuContexto.add(itemExcluir);

            // Correção: Use o componente associado à tabela, não o modelo

            menuContexto.show(tiposTable, x, y);
        }
    }
    public void limparTabela() {
        tableModel.setRowCount(0);
    }

    private void atualizar(){
        limparTabela();
        List<TipoItem> list = tipoItemDao.findAll();
        for (TipoItem tipoItem : list) {
            adicionarTipoTabela(tipoItem);
        }
    }
    private void adicionarTipoTabela(TipoItem tipo) {
        tableModel.addRow(new Object[]{tipo.getId(), tipo.getTipo()});
    }
    private void cadastrar(String tipo){
        if(tipo.isEmpty()){
            JOptionPane.showMessageDialog(null, "Campo obrigatório não preenchido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Boolean achou = false;
        List<TipoItem> list;
        list = tipoItemDao.findAll();
        for (TipoItem obj : list) {
            String tipoSemAcento = removeAcentos(tipo.toLowerCase());
            String tipoObjSemAcento = removeAcentos(obj.getTipo().toLowerCase());
            if (tipoSemAcento.equalsIgnoreCase(tipoObjSemAcento)){
                achou = true;
            }
        }
        if(achou){
            JOptionPane.showMessageDialog(null, "Tipo já registrado.", "Aviso", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tipo = maisculaPrimeira(tipo);

        TipoItem tipoItem = new TipoItem(null, tipo);
        tipoItemDao.insert(tipoItem);
        atualizar();
    }
    private static String maisculaPrimeira(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Retorna a string original se for nula ou vazia
        }

        // Converte a primeira letra para maiúscula e as letras subsequentes para minúscula
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    private String removeAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    private void atualizarItem(int Id,String tipo){
        String tipoSemAcento = removeAcentos(tipo.toLowerCase());

        Boolean achou = false;
        List<TipoItem> list;
        list = tipoItemDao.findAll();
        for (TipoItem obj : list) {
            String tipoObjSemAcento = removeAcentos(obj.getTipo().toLowerCase());
            if(!(Id == obj.getId())) {
                if (tipoSemAcento.equalsIgnoreCase(tipoObjSemAcento)) {
                    achou = true;
                    atualizar();
                    JOptionPane.showMessageDialog(TelaTipoItem.this, "Tipo já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        }
        if(!achou){
            TipoItem tipoItem = tipoItemDao.findById(Id);
            tipoItem.setTipo(tipo);
            tipoItemDao.update(tipoItem);
            return;
        }
        atualizar();
    }
    public static void exibirTela() {
        SwingUtilities.invokeLater(() -> {
            if (instance == null) {
                instance = new TelaTipoItem();
            }
            instance.setSize(500, 525);
            instance.setVisible(true);
        });
    }
}