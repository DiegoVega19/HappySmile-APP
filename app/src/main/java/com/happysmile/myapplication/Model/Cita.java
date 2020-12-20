package com.happysmile.myapplication.Model;

public class Cita {
    public  int id;
    public String fechaPropuesta;
    public String horaPropuesta;
    public int estado_citas_id;
    public String nombrePaciente;
    public  String apellidoPaciente;
    public String doctorNombre;
    public  String servicio;
    public  String estadoCita;
    public  int totalCitas;

    public int getId() {
        return id;
    }

    public String getFechaPropuesta() {
        return fechaPropuesta;
    }

    public String getHoraPropuesta() {
        return horaPropuesta;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public String getDoctorNombre() {
        return doctorNombre;
    }

    public String getServicio() {
        return servicio;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public int getEstado_citas_id() {
        return estado_citas_id;
    }

    public int getTotalCitas() {
        return totalCitas;
    }

    @Override
    public String toString() {
        return "Cita{" +
                 fechaPropuesta  +
               horaPropuesta  +
                 estado_citas_id +
              nombrePaciente  +
               apellidoPaciente  +
               doctorNombre  +
               servicio  +
               estadoCita +
                totalCitas +
                '}';
    }


}
