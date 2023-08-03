import { Input, OnDestroy } from '@angular/core';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { NbThemeService } from '@nebular/theme';
import { ResultBarSettings } from './result-bar.model';
import * as echarts from 'echarts';
import { Observable } from 'rxjs';

@Component({
  selector: 'ngx-result-bar',
  templateUrl: './result-bar.component.html',
  styleUrls: ['./result-bar.component.scss']
})
export class ResultBarComponent implements OnInit {
  options: any = {};
  title: string;
  @Input() onResultBarSettingsChange: Observable<ResultBarSettings>;

  constructor(private theme: NbThemeService) {
  }

  ngOnInit(): void {
    const updateFrequency = 2000;
    const dimension = 0;
    this.onResultBarSettingsChange.subscribe(resultBarSettings => {
    this.title = resultBarSettings.title;
    const value_array = new Array();
    if(resultBarSettings.data)
    {
      resultBarSettings.data.forEach(element => {

        if(element.valor !== undefined) {
          value_array.push(new Array(element.valor.toFixed(2),element.texto_formateado,element.color));
        } else {
          value_array.push(new Array(0,element.texto_formateado,'#FFFFFF'));
        }

      });



    }
    let data =  [[]];

    if(value_array.length > 0){
      data = value_array.sort((a, b) => b[0] - a[0]);
    }

    data = [["","",""]].concat(data); 
    
    this.options = {
        responsive: true,
        maintainAspectRatio: false,
        grid: {
            top: 10,
            bottom: 30,
            left: 210,
            right: 50

        },
        xAxis: {
            max: 'dataMax',
            label: {
                formatter: function (n) {
                    return Math.round(n);
                }
            }
        },
        dataset: {
            source: data
        },
        yAxis: {
            type: 'category',
            inverse: true,
            max: 6 ,
            axisLabel: {
                show: true,
                fontWeight: '700',
                fontSize: 11,
                textStyle: {
                    fontSize: 30
                },
                formatter: function (value) {
                    return value ;
                },
                rich: {
                    flag: {
                        fontSize: 10,
                        padding: 5
                    }
                }
            },
            animationDuration: 300,
            animationDurationUpdate: 300
        },
        series: [{
            realtimeSort: true,
            seriesLayoutBy: 'column',
            type: 'bar',
            itemStyle: {
                color: function (param) {

                    return param.value[2] || '#5470c6';
                }
            },
            encode: {
                x: dimension,
                y: 3
            },
            label: {
                fontWeight: '700',
                show: true,
                precision: 1,
                position: 'right',
                valueAnimation: true,
                fontFamily: 'monospace'
            }
        }],
        // Disable init animation.
        animationDuration: 0,
        animationDurationUpdate: updateFrequency,
        animationEasing: 'linear',
        animationEasingUpdate: 'linear',
        graphic: {
            elements: [{
                type: 'text',
                right: 160,
                bottom: 60,
                style: {
                    text: '',
                    font: 'bolder 80px monospace',
                    fill: 'rgba(100, 100, 100, 0.25)'
                },
                z: 100
            }]
        }
      }});
    }
  }