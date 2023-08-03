export interface Circuit {
  nombre: string;
  totalcargados:  number;
  totaltelegramas: number;
}

export interface CircuitSettings {
  title: string;
  circuits: Circuit[];
}
