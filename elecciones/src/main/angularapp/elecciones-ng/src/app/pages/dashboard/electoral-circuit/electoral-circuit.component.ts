import { Circuit, CircuitSettings } from './circuit.model';
import { Component, Input, OnInit, AfterViewInit } from '@angular/core';
import { NbThemeService } from '@nebular/theme';
import { delay, takeWhile } from 'rxjs/operators';
import { Subject } from 'rxjs/Rx';

@Component({
  selector: 'ngx-electoral-circuit',
  templateUrl: './electoral-circuit.component.html',
  styleUrls: ['./electoral-circuit.component.scss']
})
export class ElectoralCircuitComponent implements OnInit, AfterViewInit {

  private alive = true;

  @Input('circuitSubjectSettings') circuitSubjectSettings: Subject<CircuitSettings>;;

  public circuitSettings: CircuitSettings;

  currentTheme: string;
  themeSubscription: any;


  constructor(private themeService: NbThemeService) {

    this.themeService.getJsTheme()
      .pipe(takeWhile(() => this.alive))
      .subscribe(theme => {
        this.currentTheme = theme.name;
    });
  }

  ngAfterViewInit(): void {

  }

  ngOnInit(): void {
    this.circuitSubjectSettings.pipe(delay(0)).subscribe((circuitSettings: CircuitSettings) =>{
            this.circuitSettings = circuitSettings;
    });

  }
}
