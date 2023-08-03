import { Circuit, CircuitSettings } from './electoral-circuit/circuit.model';
import { TelegramSettings } from './telegrams-card/telegram.model';
import { Component, OnDestroy, OnInit} from '@angular/core';
import { Subject, Subscription } from 'rxjs-compat';
import { ResultBarSettings } from './result-bar/result-bar/result-bar.model';
import { WebSocketAPI } from './service/web-socket.api';
import {cloneDeep} from 'lodash';
import { ResultService } from './service/result.service';
import { interval } from 'rxjs';
interface CardSettings {
  title: string;
  iconClass: string;
  type: string;
}
@Component({
  selector: 'ngx-dashboard',
  styleUrls: ['./dashboard.component.scss'],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnDestroy, OnInit {



  public changeBarSenador: Subject<ResultBarSettings> = new Subject<ResultBarSettings>();
  public changeBarDiputado: Subject<ResultBarSettings> = new Subject<ResultBarSettings>();
  public changeBarConcejal: Subject<ResultBarSettings> = new Subject<ResultBarSettings>();

  public changeBarPresidente: Subject<ResultBarSettings> = new Subject<ResultBarSettings>();
  public changeBarGobernador: Subject<ResultBarSettings> = new Subject<ResultBarSettings>();
  public changeBarParlamentariosMercosur: Subject<ResultBarSettings> = new Subject<ResultBarSettings>();
  public changeBarIntendente: Subject<ResultBarSettings> = new Subject<ResultBarSettings>();

  loadedCircuit: Circuit[] = [] ;

  public resultBarSettingDiputadosNacionales: ResultBarSettings = {
    title: "DIPUTADOS NACIONALES",
    data: [[]]
  }

  public resultBarSettingLegisladoresProvinciales: ResultBarSettings = {
    title: "LEGISLADORES PROVINCIALES",
    data: [[]]
  }
  public resultBarSettingConcejales: ResultBarSettings = {
    title: "CONCEJALES",
    data: [[]]
  }

  public resultBarSettingPresidente: ResultBarSettings = {
    title: "PRESIDENTE",
    data: [[]]
  }

  public resultBarSettingGobernador: ResultBarSettings = {
    title: "GOBERNADOR",
    data: [[]]
  }

  public resultBarSettingParlamentarios: ResultBarSettings = {
    title: "PARLAMENTARIOS MERCOSUR",
    data: [[]]
  }

  public resultBarSettingIntendente: ResultBarSettings = {
    title: "INTENDENTE",
    data: [[]]
  }

  public resultBarSettingSenadores: ResultBarSettings = {
    title: "SENADORES",
    data: [[]]
  }


  circuitLoadedSubjectSettings = new Subject<CircuitSettings>()

  public circuitLoadedSettings: CircuitSettings = {
    title: 'CIRCUITOS ESCRUTADOS',
    circuits :[]
  };

  circuitToLoadSubjectSettings = new Subject<CircuitSettings>()

  public circuitToLoadSettings: CircuitSettings = {
    title: 'CIRCUITOS A ESCRUTAR',
    circuits :[]
  };

  telegramLoadedSubjectSettings = new Subject<TelegramSettings>()

  public loadedTelegramSettings: TelegramSettings = {
      title: 'MESAS ESCRUTADAS',
      telegrams : 0,
      totalTelegrams: 0
  };

  telegramToLoadSubjectSettings = new Subject<TelegramSettings>()
  public telegramToLoadSettings: TelegramSettings = {
    title: 'MESAS A ESCRUTAR',
    telegrams : 0,
    totalTelegrams: 0
  };


  private alive = true;

  solarValue: number;


  public messages: Subject<any>;
  public webSocketAPI: WebSocketAPI;
  public subjectdashBoard = new Subject<any>();

  private subscription: Subscription;

  constructor(private resultService: ResultService ) {}

    stompClient: any;

    updateResults(result:any){
      this.loadedTelegramSettings.telegrams = result.totalTelegramsLoaded;
      this.loadedTelegramSettings.totalTelegrams = result.totalTables;
      this.telegramLoadedSubjectSettings.next(this.loadedTelegramSettings);
      this.telegramToLoadSettings.telegrams = result.totalTables - result.totalTelegramsLoaded;
      this.telegramToLoadSettings.totalTelegrams = result.totalTables;
      this.telegramToLoadSubjectSettings.next(this.telegramToLoadSettings);
      this.circuitLoadedSettings.circuits = result.totalesxCircuito
      this.circuitLoadedSubjectSettings.next(this.circuitLoadedSettings);
      this.circuitToLoadSettings.circuits =  cloneDeep(result.totalesxCircuito);
      this.circuitToLoadSettings.circuits.forEach(circuit => circuit.totalcargados = circuit.totaltelegramas - circuit.totalcargados);
      this.circuitToLoadSubjectSettings.next(this.circuitToLoadSettings);

      this.resultBarSettingLegisladoresProvinciales.data = result.totalesxLegisladoresProvinciales;
      this.changeBarSenador.next(this.resultBarSettingLegisladoresProvinciales);

      this.resultBarSettingDiputadosNacionales.data = result.totalesxDiputadosNacionales;
      this.changeBarDiputado.next(this.resultBarSettingDiputadosNacionales);

      this.resultBarSettingConcejales.data = result.totalesxConcejales;
      this.changeBarConcejal.next(this.resultBarSettingConcejales);

      this.resultBarSettingPresidente.data = result.totalesxPresidente;     //PRESIDENTE
      this.changeBarPresidente.next(this.resultBarSettingPresidente);

      this.resultBarSettingGobernador.data = result.totalesxGobernador;     //GOBERNADOR
      this.changeBarGobernador.next(this.resultBarSettingGobernador);

      this.resultBarSettingIntendente.data = result.totalesxIntendente;     //INTENDENTE
      this.changeBarIntendente.next(this.resultBarSettingIntendente);

      this.resultBarSettingParlamentarios.data = result.totalesxParlamentarios;     //PARLAMENTARIOS
      this.changeBarParlamentariosMercosur.next(this.resultBarSettingParlamentarios);

      this.resultBarSettingSenadores.data = result.totalesxSenadores;     //SENADORES
      this.changeBarSenador.next(this.resultBarSettingSenadores);

    }

    ngOnInit(): void {
      this.resultService.getResults().subscribe(result => {
        this.updateResults(result);
      });

      const source = interval(60000);
      this.subscription = source.subscribe(val => 
        
        this.loadResults()
      );
      /*this.webSocketAPI = new WebSocketAPI(this.subjectdashBoard);
      this.webSocketAPI._connect();
      this.subjectdashBoard.subscribe(result => {
            this.updateResults(result);
      });*/

  }
  ngOnDestroy() {
    this.alive = false;

    this.subscription.unsubscribe();
  }

  loadResults()
  {

    this.resultService.getResults().subscribe(result => {
      console.log("llega");
      this.updateResults(result);
    });

  }


}
