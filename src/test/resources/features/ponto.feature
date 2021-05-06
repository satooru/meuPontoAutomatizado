# encoding: utf-8
# language: pt
@ponto

Funcionalidade: Preencher ponto

  Essa feature será encarregada de preencher o ponto

  Cenário: Preencher ponto
    Dado que eu realize login no portal de ponto
    E que o ponto não esteja preenchido
    Quando preencher o ponto
    Então o apontamento deverá ser contabilizado