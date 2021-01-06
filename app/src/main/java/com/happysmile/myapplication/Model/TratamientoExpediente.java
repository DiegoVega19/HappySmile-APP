package com.happysmile.myapplication.Model;

public class TratamientoExpediente {
        public String fechaInicio;
        public String fechaFinal;
        public String descripcion;
        public String observacion;

        public String getFechaInicio() {
                return fechaInicio;
        }

        public void setFechaInicio(String fechaInicio) {
                this.fechaInicio = fechaInicio;
        }

        public String getFechaFinal() {
                return fechaFinal;
        }

        public void setFechaFinal(String fechaFinal) {
                this.fechaFinal = fechaFinal;
        }

        public String getDescripcion() {
                return descripcion;
        }

        public void setDescripcion(String descripcion) {
                this.descripcion = descripcion;
        }

        public String getObservacion() {
                return observacion;
        }

        public void setObservacion(String observacion) {
                this.observacion = observacion;
        }
}
