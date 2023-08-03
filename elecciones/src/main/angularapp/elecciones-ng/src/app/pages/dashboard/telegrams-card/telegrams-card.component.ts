import { TelegramSettings } from './telegram.model';
import { OnDestroy, OnInit, ViewChild, ElementRef } from '@angular/core';
import { AfterViewInit, Component, Input } from '@angular/core';
import { NbThemeService } from '@nebular/theme';
import { delay } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component({
  selector: 'ngx-telegrams-card',
  styleUrls: ['./telegrams-card.component.scss'],
  templateUrl: './telegrams-card.component.html'
})

export class TelegramsCardComponent implements AfterViewInit, OnDestroy, OnInit {


  @Input('telegramSubjectSettings') telegramSubjectSettings: Subject<TelegramSettings>;

  option: any;
  themeSubscription: any;

  telegramSettings: TelegramSettings;

  echarCardInstance: any;
  config : any;
  constructor(private theme: NbThemeService) {
  }

  ngOnInit(): void {
    this.telegramSubjectSettings.pipe(delay(0)).subscribe((telegramSettings: TelegramSettings) =>{
      this.telegramSettings = telegramSettings;
      const value = (this.telegramSettings.telegrams * 100) / this.telegramSettings.totalTelegrams;

      console.log(value);

      console.log( this.telegramSettings);


      if (this.config && !Number.isNaN(value)) {
         this.updateChar(value);
      }

});

0  }

  updateChar(value: number) {
    const solarTheme: any = this.config.variables.solar;
    this.option = Object.assign({}, {
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
      series: [
        {
          name: ' ',
          clockWise: true,
          hoverAnimation: false,
          type: 'pie',
          center: ['45%', '50%'],
          radius: solarTheme.radius,
          data: [
            {
              value: value,
              name: ' ',
              label: {
                normal: {
                  position: 'center',
                  formatter: '{d}%',
                  textStyle: {
                    fontSize: '22',
                    fontFamily: this.config.variables.fontSecondary,
                    fontWeight: '600',
                    color: this.config.variables.fgHeading,
                  },
                },
              },
              tooltip: {
                show: false,
              },
              itemStyle: {
                normal: {
                  color: '#ffd205',
                  shadowColor: solarTheme.shadowColor,
                  shadowBlur: 0,
                  shadowOffsetX: 0,
                  shadowOffsetY: 3,
                },
              },
              hoverAnimation: false,
            },
            {
              value: 100 - value,
              name: ' ',
              tooltip: {
                show: false,
              },
              label: {
                normal: {
                  position: 'inner',
                },
              },
              itemStyle: {
                normal: {
                  color: '#ffd205',
                },
              },
            },
          ],
        },
        {
          name: ' ',
          clockWise: true,
          hoverAnimation: false,
          type: 'pie',
          center: ['45%', '50%'],
          radius: solarTheme.radius,
          data: [
            {
              value: value,
              name: ' ',
              label: {
                normal: {
                  position: 'inner',
                  show: false,
                },
              },
              tooltip: {
                show: false,
              },
              itemStyle: {
                normal: {
                  color: '#f06525'
                },
              },
              hoverAnimation: false,
            },
            {
              value: 100 - value,
              name: ' ',
              tooltip: {
                show: false,
              },
              label: {
                normal: {
                  position: 'inner',
                },
              },
              itemStyle: {
                normal: {
                  color: 'none',
                },
              },
            },
          ],
        },
      ],
    });
  }

  ngAfterViewInit() {
    this.themeSubscription = this.theme.getJsTheme().pipe(delay(1)).subscribe(config => {
      this.config = config;
    });
  }


  ngOnDestroy() {
    this.themeSubscription.unsubscribe();
  }
}
