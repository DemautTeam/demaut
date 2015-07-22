#language: fr

@annexes
Fonctionnalité: Attacher une ou plusieurs annexes à une demande nouvelle

  Scénario: Attacher une annexe valide à une demande nouvelle
    Etant donné le demandeur fait une nouvelle demande
    Lorsque ce dernier veut attacher une annexe à cette demande
    Alors le system Demaut valide av que l annexe est valide
    Alors le systeme stocke l annexe et confirme la bonne reception de l annexe
