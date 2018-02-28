package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    //Configurar sons
    private Sound somBaterAsas;
    private Sound somMarcarPontos;
    private Sound somColisaoCano;
    private Sound somQueda;
    private boolean somGameOver;

    private SpriteBatch batch; //Classe usada para criar animações
    private Texture[] passaros; //Array de texturas
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoTopo;
    private Texture gameOver;
    private Random numeroRandomico;
    private BitmapFont fonte; //Classe usada para desenhar textos
    private BitmapFont mensagem;
    private Circle passaroCirculo;
    private Rectangle retanguloCanoTopo;
    private Rectangle retanguloCanoBaixo;
    //private ShapeRenderer shape; //Desenhar formas na tela

    //Atributos de configuração
    private float larguraTela;
    private float alturaTela;
    private int estadoJogo = 0; //0-> Jogo não iniciado, 1-> Jogo iniciado, 2-> Game Over
    private int pontuacao = 0;

    private float variacao = 0;
    private float velocidadeQueda = 0;
    private float posicaoInicialVertical = 0;
    private float posicaoMovimentoCanoHorizontal;
    private float espacoEntreCanos;
    private float deltaTime;
    private float alturaCanoRandomica;
    private boolean marcouPonto = false;

    //Câmera
    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRTUAL_WIDTH = 768;
    private final float VIRTUAL_HEIGHT = 1024;
	
	@Override
	public void create () {

        batch = new SpriteBatch();
        numeroRandomico = new Random();
        passaroCirculo = new Circle();
        //retanguloCanoTopo = new Rectangle();
        //retanguloCanoBaixo = new Rectangle();
        //shape = new ShapeRenderer();

        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);

        mensagem = new BitmapFont();
        mensagem.setColor(Color.WHITE);
        mensagem.getData().setScale(3);

        passaros = new Texture[3];
        passaros[0] = new Texture("passaro1.png");
        passaros[1] = new Texture("passaro2.png");
        passaros[2] = new Texture("passaro3.png");

        gameOver = new Texture("game_over.png");
        fundo = new Texture("fundo.png");
        canoBaixo = new Texture("cano_baixo.png");
        canoTopo = new Texture("cano_topo.png");

        //Instanciar sons
        somBaterAsas = Gdx.audio.newSound(Gdx.files.internal("data/sfx_wing.wav"));
        somMarcarPontos = Gdx.audio.newSound(Gdx.files.internal("data/sfx_point.wav"));
        somColisaoCano = Gdx.audio.newSound(Gdx.files.internal("data/sfx_hit.wav"));
        somQueda = Gdx.audio.newSound(Gdx.files.internal("data/sfx_die.wav"));

        /*******************************************
         * Configuração da câmera
         * */
        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, 0);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera); //Largura, altura e camera

        larguraTela = VIRTUAL_WIDTH; //Código antigo: Gdx.graphics.getWidth()
        alturaTela = VIRTUAL_HEIGHT; //Código antigo: Gdx.graphics.getHeight();
        posicaoInicialVertical = alturaTela / 2;
        posicaoMovimentoCanoHorizontal = larguraTela;
        espacoEntreCanos = 300;
	}

	@Override
	public void render () {

        camera.update();

        //Limpar frames anteriores - economiza memória
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //DeltaTime calcula a diferença de tempo entre cada execução do render
        //São valores muito pequenos 0.1214, 0.16768...etc
        deltaTime = Gdx.graphics.getDeltaTime();

        //Configurar velocidade das animações
        variacao += deltaTime * 10; //Bater de asas

        //Gdx.app.log("Variacao", "Variacao: " + Gdx.graphics.getDeltaTime()); //Exibir no log
        if (variacao > 2) variacao = 0;

        if(estadoJogo==0){ //Jogo não iniciado

            if(Gdx.input.justTouched()){
                estadoJogo = 1;
            }

        }else { //Jogo iniciado

            velocidadeQueda++; //Velocidade de queda do pássaro
            if (posicaoInicialVertical > 20 || velocidadeQueda < 0)
                posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda; //Deburrar passáro*/

            if(estadoJogo == 1){

                somGameOver = true; //Se bater em um cano, então pode tocar som de game over

                posicaoMovimentoCanoHorizontal -= deltaTime * 200; //Velocidade dos canos

                //Método para capturar evento de Touch
                if (Gdx.input.justTouched()) {

                    if(posicaoInicialVertical<alturaTela) {
                        velocidadeQueda = -15;
                        somBaterAsas.play();
                    }else{ //Impedir que o passáro ultrapasse a altura da tela
                        posicaoInicialVertical = alturaTela + 20;
                    }
                }

                //Verica se o cano saiu inteiramente da tela
                if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {

                    posicaoMovimentoCanoHorizontal = larguraTela;

                    //configura nova altura dos canos
                    alturaCanoRandomica = numeroRandomico.nextInt(400) - 200; //Assim gera números negativos e positivos
                    marcouPonto = false; //Recebe false toda vez que um novo cano é criado
                }

                //Incrementa pontuação
                if(posicaoMovimentoCanoHorizontal < 120) { //x = 120 é a posição do pássaro
                    if (!marcouPonto) {
                        pontuacao++;
                        marcouPonto = true;
                        somMarcarPontos.play();
                    }
                }
            }else { //Jogo em Game Over

                if(Gdx.input.justTouched()){ //Reiniciar jogo
                    estadoJogo = 0;
                    pontuacao = 0;
                    velocidadeQueda = 0;
                    posicaoInicialVertical = alturaTela/2;
                    posicaoMovimentoCanoHorizontal = larguraTela;
                }
            }

        }

        //Configurar dados de projeção da câmera
        batch.setProjectionMatrix(camera.combined);

        //Iniciar exibição das imagens
        batch.begin();

        batch.draw(fundo,0,0, larguraTela, alturaTela); //img, x, y, largura, altura
        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaTela/2 + espacoEntreCanos/2 + alturaCanoRandomica);
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, (alturaTela/2 - canoBaixo.getHeight()) - espacoEntreCanos/2 + alturaCanoRandomica);

        if(estadoJogo!=2) {
            batch.draw(passaros[(int) variacao], 120, posicaoInicialVertical); //img, x, y
        }else{ //Parar de bater asas do pássaro no Game over
            batch.draw(passaros[0],120, posicaoInicialVertical);
        }
        fonte.draw(batch, String.valueOf(pontuacao), larguraTela/2, alturaTela - 50);

        //Tela de Game Over
        if(estadoJogo==2){
            batch.draw(gameOver, larguraTela/2 - gameOver.getWidth()/2, alturaTela/2);
            mensagem.draw(batch, "Toque para reiniciar!" ,larguraTela/2 - 200, alturaTela/2 - gameOver.getHeight()/2);
        }

        batch.end();

        //x, y e raio
        passaroCirculo.set(120 + passaros[0].getWidth()/2, posicaoInicialVertical + passaros[0].getHeight()/2, passaros[0].getWidth()/2);


        //x, y, width, height
        retanguloCanoBaixo = new Rectangle(
                posicaoMovimentoCanoHorizontal,
                alturaTela/2 - canoBaixo.getHeight() - espacoEntreCanos/2 + alturaCanoRandomica,
                canoBaixo.getWidth(),
                canoBaixo.getHeight()
        );

        //x, y, width, height
        retanguloCanoTopo = new Rectangle(
                posicaoMovimentoCanoHorizontal,
                alturaTela/2 + espacoEntreCanos/2 + alturaCanoRandomica,
                canoBaixo.getWidth(),
                canoBaixo.getHeight()
        );


        //Desenhar shapes na tela
        /*
        shape.begin(ShapeRenderer.ShapeType.Filled); //Forma preenchia
        shape.circle(passaroCirculo.x, passaroCirculo.y, passaroCirculo.radius);
        shape.rect(retanguloCanoBaixo.x, retanguloCanoBaixo.y, retanguloCanoBaixo.width, retanguloCanoBaixo.height);
        shape.rect(retanguloCanoTopo.x, retanguloCanoTopo.y, retanguloCanoTopo.width, retanguloCanoTopo.height);
        shape.setColor(Color.RED);
        shape.end();
        */

        //Teste de colisão
        if(Intersector.overlaps(passaroCirculo, retanguloCanoBaixo) || Intersector.overlaps(passaroCirculo, retanguloCanoTopo)
                || posicaoInicialVertical <=20){

            estadoJogo = 2;

            //Evitar loop no som
            if(somGameOver){
                somColisaoCano.play();
                somQueda.play();
                somGameOver = false;
            }

        }

	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
