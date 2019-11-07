import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

// Rutas
import { APP_ROUTING } from './app.routes';

// Components
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { CitasComponent } from './components/citas/citas.component';
import { RegistroComponent } from './components/registro/registro.component';
import { PedircitaComponent } from './components/pedircita/pedircita.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'



// Services
import { CitaService } from './services/cita.service';
import { UsuarioService } from './services/usuario.service';
import { FooterComponent } from './components/footer/footer.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CitasComponent,
    RegistroComponent,
    PedircitaComponent,
    NavbarComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule, 
    HttpClientModule,
    FormsModule,
    APP_ROUTING
  ],
  providers: [
    CitaService,
    UsuarioService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
