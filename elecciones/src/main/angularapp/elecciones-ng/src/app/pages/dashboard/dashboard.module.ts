import { NgModule } from '@angular/core';
import {
  NbActionsModule,
  NbButtonModule,
  NbCardModule,
  NbTabsetModule,
  NbUserModule,
  NbRadioModule,
  NbSelectModule,
  NbListModule,
  NbIconModule,
  NbProgressBarComponent,
  NbProgressBarModule,
} from '@nebular/theme';
import { NgxEchartsModule } from 'ngx-echarts';

import { ThemeModule } from '../../@theme/theme.module';
import { DashboardComponent } from './dashboard.component';
import { FormsModule } from '@angular/forms';
import { TelegramsCardComponent } from './telegrams-card/telegrams-card.component';
import { ElectoralCircuitComponent } from './electoral-circuit/electoral-circuit.component';
import { RoundPipe } from './electoral-circuit/round.pipe';
import { ResultBarComponent } from './result-bar/result-bar/result-bar.component';

@NgModule({
  imports: [
    FormsModule,
    ThemeModule,
    NbCardModule,
    NbUserModule,
    NbButtonModule,
    NbTabsetModule,
    NbActionsModule,
    NbRadioModule,
    NbSelectModule,
    NbListModule,
    NbIconModule,
    NbButtonModule,
    NbProgressBarModule,
    NgxEchartsModule,


  ],
  declarations: [
    DashboardComponent,
    TelegramsCardComponent,
    ElectoralCircuitComponent,
    RoundPipe,
    ResultBarComponent
  ],
})
export class DashboardModule { }
