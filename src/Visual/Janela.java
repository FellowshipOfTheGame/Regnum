/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;

import Controle.Controle;
import Visual.Plano.Tela2D;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;

/**
 *
 * @author marcius
 */
public class Janela extends javax.swing.JFrame {

    private Controle controle;
//    private Tela3D listener;
    private Tela2D painel;
    private Color corAtor;

    public Janela() {
        this.controle = Controle.instanciaControle();

        this.painel = new Tela2D();
        painel.setInicio();
        painel.setTime();
        painel.setSala();
        painel.setJogo();
        painel.setOutroJogador();
        painel.setOpcao();
        painel.setVencedor();

        initComponents();

        jPAtor.setVisible(false);
        jPRede.setVisible(false);
        jBIniciarPartida.setVisible(false);
        jTAChat.setVisible(false);
        jTMensagem.setVisible(false);
        jBMensagem.setVisible(false);
        
        //esse objetos estão invisivei pois nao esta implementado o grava e assistir
        //TODO gravar e assistir
        jCBGravar.setVisible(false);
        jRBAssistirPartida.setVisible(false);

        corAtor = Color.BLUE;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPainel = painel;
        jBIniciarPartida = new javax.swing.JButton();
        jPInicial = new javax.swing.JPanel();
        jRBRede = new javax.swing.JRadioButton();
        jRBAssistirPartida = new javax.swing.JRadioButton();
        jBOKInicial = new javax.swing.JButton();
        jPRede = new javax.swing.JPanel();
        jRBServidor = new javax.swing.JRadioButton();
        jRBCliente = new javax.swing.JRadioButton();
        jBOKRede = new javax.swing.JButton();
        jTFIP = new javax.swing.JTextField();
        jPAtor = new javax.swing.JPanel();
        jTFTime = new javax.swing.JTextField();
        jBOKAtor = new javax.swing.JButton();
        jCBGravar = new javax.swing.JCheckBox();
        jBCor = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAChat = new javax.swing.JTextArea();
        jTMensagem = new javax.swing.JTextField();
        jBMensagem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Regnum");
        setMaximumSize(new java.awt.Dimension(2024, 1000));
        setMinimumSize(new java.awt.Dimension(600, 600));
        setPreferredSize(new java.awt.Dimension(600, 750));

        jPainel.setMaximumSize(new java.awt.Dimension(2000, 1000));
        jPainel.setMinimumSize(new java.awt.Dimension(1, 1));
        jPainel.setOpaque(false);
        jPainel.setPreferredSize(new java.awt.Dimension(600, 520));
        jPainel.setRequestFocusEnabled(false);

        jBIniciarPartida.setText("Iniciar Partida");
        jBIniciarPartida.setPreferredSize(new java.awt.Dimension(300, 30));
        jBIniciarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBIniciarPartidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPainelLayout = new javax.swing.GroupLayout(jPainel);
        jPainel.setLayout(jPainelLayout);
        jPainelLayout.setHorizontalGroup(
            jPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPainelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBIniciarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPainelLayout.setVerticalGroup(
            jPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPainelLayout.createSequentialGroup()
                .addContainerGap(478, Short.MAX_VALUE)
                .addComponent(jBIniciarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPInicial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPInicial.setMaximumSize(new java.awt.Dimension(34, 40));
        jPInicial.setMinimumSize(new java.awt.Dimension(34, 40));
        jPInicial.setPreferredSize(new java.awt.Dimension(34, 40));

        jRBRede.setSelected(true);
        jRBRede.setText("Jogar em Rede");
        jRBRede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBRedeActionPerformed(evt);
            }
        });

        jRBAssistirPartida.setText("Assistir Partida");
        jRBAssistirPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBAssistirPartidaActionPerformed(evt);
            }
        });

        jBOKInicial.setText("OK");
        jBOKInicial.setMaximumSize(new java.awt.Dimension(34, 40));
        jBOKInicial.setMinimumSize(new java.awt.Dimension(34, 30));
        jBOKInicial.setPreferredSize(new java.awt.Dimension(34, 30));
        jBOKInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOKInicialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPInicialLayout = new javax.swing.GroupLayout(jPInicial);
        jPInicial.setLayout(jPInicialLayout);
        jPInicialLayout.setHorizontalGroup(
            jPInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPInicialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRBRede)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRBAssistirPartida)
                .addGap(36, 36, 36)
                .addComponent(jBOKInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPInicialLayout.setVerticalGroup(
            jPInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jBOKInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addComponent(jRBAssistirPartida)
                .addComponent(jRBRede))
        );

        jPRede.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPRede.setPreferredSize(new java.awt.Dimension(559, 50));

        jRBServidor.setSelected(true);
        jRBServidor.setText("Servidor");
        jRBServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBServidorActionPerformed(evt);
            }
        });

        jRBCliente.setText("Cliente");
        jRBCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBClienteActionPerformed(evt);
            }
        });

        jBOKRede.setText("OK");
        jBOKRede.setMaximumSize(new java.awt.Dimension(34, 40));
        jBOKRede.setMinimumSize(new java.awt.Dimension(34, 40));
        jBOKRede.setPreferredSize(new java.awt.Dimension(34, 40));
        jBOKRede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOKRedeActionPerformed(evt);
            }
        });

        jTFIP.setText("localhost");

        javax.swing.GroupLayout jPRedeLayout = new javax.swing.GroupLayout(jPRede);
        jPRede.setLayout(jPRedeLayout);
        jPRedeLayout.setHorizontalGroup(
            jPRedeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPRedeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRBServidor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRBCliente)
                .addGap(53, 53, 53)
                .addComponent(jTFIP, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jBOKRede, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPRedeLayout.setVerticalGroup(
            jPRedeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPRedeLayout.createSequentialGroup()
                .addGroup(jPRedeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBOKRede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRBCliente)
                    .addComponent(jRBServidor))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        jPAtor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPAtor.setPreferredSize(new java.awt.Dimension(567, 50));

        jTFTime.setText("Time");

        jBOKAtor.setText("OK");
        jBOKAtor.setMaximumSize(new java.awt.Dimension(34, 40));
        jBOKAtor.setMinimumSize(new java.awt.Dimension(34, 40));
        jBOKAtor.setPreferredSize(new java.awt.Dimension(34, 40));
        jBOKAtor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOKAtorActionPerformed(evt);
            }
        });

        jCBGravar.setText("Gravar");
        jCBGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBGravarActionPerformed(evt);
            }
        });

        jBCor.setText("COR");
        jBCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPAtorLayout = new javax.swing.GroupLayout(jPAtor);
        jPAtor.setLayout(jPAtorLayout);
        jPAtorLayout.setHorizontalGroup(
            jPAtorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAtorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTFTime, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBCor, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jCBGravar)
                .addGap(36, 36, 36)
                .addComponent(jBOKAtor, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPAtorLayout.setVerticalGroup(
            jPAtorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAtorLayout.createSequentialGroup()
                .addGroup(jPAtorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBOKAtor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBGravar)
                    .addComponent(jBCor)
                    .addComponent(jTFTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        jScrollPane1.setAutoscrolls(true);

        jTAChat.setEditable(false);
        jTAChat.setColumns(20);
        jTAChat.setRows(5);
        jTAChat.setWrapStyleWord(true);
        jTAChat.setDragEnabled(true);
        jTAChat.setEnabled(false);
        jTAChat.setMaximumSize(new java.awt.Dimension(600, 100));
        jTAChat.setMinimumSize(new java.awt.Dimension(600, 100));
        jTAChat.setPreferredSize(new java.awt.Dimension(600, 10000000));
        jScrollPane1.setViewportView(jTAChat);

        jTMensagem.setMaximumSize(new java.awt.Dimension(400, 50));
        jTMensagem.setMinimumSize(new java.awt.Dimension(400, 50));
        jTMensagem.setPreferredSize(new java.awt.Dimension(400, 50));

        jBMensagem.setText("Enviar Mensagem");
        jBMensagem.setMaximumSize(new java.awt.Dimension(200, 29));
        jBMensagem.setMinimumSize(new java.awt.Dimension(200, 29));
        jBMensagem.setPreferredSize(new java.awt.Dimension(200, 29));
        jBMensagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMensagemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPAtor, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
            .addComponent(jPRede, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
            .addComponent(jPInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPainel, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPRede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPAtor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTMensagem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBOKRedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOKRedeActionPerformed
        this.controle.setSala(this.jTFIP.getText(), this.jRBServidor.isSelected());
        this.controle.setEstado(Controle.CONFIGURANDO);

        this.jPRede.setVisible(false);
        this.jPAtor.setVisible(true);

        this.inciaSala();

        this.repaint();
    }//GEN-LAST:event_jBOKRedeActionPerformed

    private void jBOKInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOKInicialActionPerformed
        if (this.jRBAssistirPartida.isSelected()) {
            this.controle.setEstado(Controle.ASSISTINDO);
            //FileChooser.getOpenDialog();
            gravarGame();
        } else {
            this.jPRede.setVisible(true);
            this.controle.setEstado(Controle.INICIANDO);
        }
        this.controle.setAssistindo(this.jRBAssistirPartida.isSelected());
        this.jPInicial.setVisible(false);

        repaint();
    }//GEN-LAST:event_jBOKInicialActionPerformed

    private void jRBRedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBRedeActionPerformed
        this.jRBRede.setSelected(true);
        this.jRBAssistirPartida.setSelected(false);
        repaint();
    }//GEN-LAST:event_jRBRedeActionPerformed

    private void jRBAssistirPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBAssistirPartidaActionPerformed
        this.jRBAssistirPartida.setSelected(true);
        this.jRBRede.setSelected(false);
        repaint();
    }//GEN-LAST:event_jRBAssistirPartidaActionPerformed

    private void jRBServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBServidorActionPerformed
        this.jRBServidor.setSelected(true);
        this.jRBCliente.setSelected(false);
        repaint();
    }//GEN-LAST:event_jRBServidorActionPerformed

    private void jRBClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBClienteActionPerformed
        this.jRBCliente.setSelected(true);
        this.jRBServidor.setSelected(false);
        repaint();
    }//GEN-LAST:event_jRBClienteActionPerformed

    private void jBCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCorActionPerformed
        corAtor = JColorChooser.showDialog(Janela.this, "Escolher a color", corAtor);
        corAtor = (corAtor==null)? Color.BLUE: corAtor;
        controle.setUsuario(jTFTime.getText(), corAtor);
        this.painel.iniciarTela();

        repaint();
    }//GEN-LAST:event_jBCorActionPerformed

    private void jBOKAtorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOKAtorActionPerformed
        controle.setUsuario(jTFTime.getText(), corAtor);
        controle.conectaCliente();

        if (this.jCBGravar.isSelected()) {
            //FileChooser.getSaveDialog();
            gravarGame();
        }

        repaint();
    }//GEN-LAST:event_jBOKAtorActionPerformed

    private void jCBGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBGravarActionPerformed
        this.controle.setGavar(this.jCBGravar.isSelected());
        repaint();
    }//GEN-LAST:event_jCBGravarActionPerformed

    private void jBIniciarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBIniciarPartidaActionPerformed
        if (this.controle.getNJogadores() > 1) {
            this.controle.noficaPartidaIniciada();
        }
    }//GEN-LAST:event_jBIniciarPartidaActionPerformed

    private void jBMensagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMensagemActionPerformed
        this.controle.mensagemChat(this.jTMensagem.getText());
        this.jTMensagem.setText("");
    }//GEN-LAST:event_jBMensagemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCor;
    private javax.swing.JButton jBIniciarPartida;
    private javax.swing.JButton jBMensagem;
    private javax.swing.JButton jBOKAtor;
    private javax.swing.JButton jBOKInicial;
    private javax.swing.JButton jBOKRede;
    private javax.swing.JCheckBox jCBGravar;
    private javax.swing.JPanel jPAtor;
    private javax.swing.JPanel jPInicial;
    private javax.swing.JPanel jPRede;
    private javax.swing.JPanel jPainel;
    private javax.swing.JRadioButton jRBAssistirPartida;
    private javax.swing.JRadioButton jRBCliente;
    private javax.swing.JRadioButton jRBRede;
    private javax.swing.JRadioButton jRBServidor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTAChat;
    private javax.swing.JTextField jTFIP;
    private javax.swing.JTextField jTFTime;
    private javax.swing.JTextField jTMensagem;
    // End of variables declaration//GEN-END:variables

    public void iniciaPartida() {
        jBIniciarPartida.setVisible(false);

        repaint();
    }

    private void inciaSala() {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        repaint();

                        Thread.sleep(1000);

                        if (controle.conectou()) {
                            controle.setEstado(Controle.SALA);

                            jPAtor.setVisible(false);
                            jTAChat.setVisible(true);
                            jTMensagem.setVisible(true);
                            jBMensagem.setVisible(true);

                            if (controle.isProvedor()) {
                                jBIniciarPartida.setVisible(true);
                            }

                            break;
                        }
                    }

                    while (true) {
                        Thread.sleep(1000);
                        repaint();

                        if (controle.getEstado() != Controle.SALA) {
                            break;
                        }
                    }
                    repaint();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    public void respostaChat(String time, String mensagemChat, Color c) {
        this.jTAChat.setDisabledTextColor(c);
        this.jTAChat.append(" " + time + ": " + mensagemChat + "\n");
    }
    
    private void gravarGame(){            
            /*Frame frame = new Frame(this.jTNomedoJogo.getText());
        //GLCanvas canvas = new GLCanvas();
//        this.listener = new Tela3D(this);
            // canvas.addGLEventListener(this.listener);
            //canvas.addKeyListener(this.listener);

            //frame.add(canvas);
            frame.setSize(640, 480);
            //final Animator animator = new Animator(canvas);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                    // make sure the call to Animator.stop() completes before
                    // exiting
                    new Thread(new Runnable() {
                        public void run() {
//                        animator.stop();
                            System.exit(0);
                        }
                    }).start();
                }
            });
            // Center frame
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
//        animator.start();

            //TODO implementar thread de leitura ou escrita
            if (this.controle.isAssistindo()) {
            } else {
            }

            this.jPCamera.setVisible(false);*/
    }
}
