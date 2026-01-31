import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Cliente } from '../../models/cliente.model';
import { ClienteService } from '../../services/cliente';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';
import { from } from 'rxjs';


@Component({
  selector: 'app-cliente',
  standalone: false,
  templateUrl: './cliente.html',
  styleUrl: './cliente.css',
})
export class ClienteComponent implements OnInit{
  
  @ViewChild('formularioCliente') formularioCliente!: ElementRef;
  @ViewChild(MatPaginator,  { static: true }) paginator!: MatPaginator;
  @ViewChild(MatSort,  { static: true }) sort!: MatSort;

  clientes: Cliente[] = [];
  cliente: Cliente = { } as Cliente;
  editar: boolean = false;
  idEditar: number | null = null;
  
  dataSource!: MatTableDataSource<Cliente>;
  mostrarColumnas: string[] = ['idCliente', 'cedula','nombre','apellido','direccion','telefono','correo','acciones'];


  constructor(private clienteService: ClienteService){ }
  
  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void{
    this.clienteService.findAll().subscribe(data => 
      //this.clientes = data
      {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    );
  }

  save(): void{
     this.clienteService.save(this.cliente).subscribe(() => { 
        this.cliente = { } as Cliente;
        this.cargarClientes();
      });
  }

  update(): void{ 
    if(this.idEditar !== null){
      this.clienteService.update(this.idEditar, this.cliente).subscribe(() => { 
        this.cliente = { } as Cliente;
        this.editar = false;
        this.idEditar = null;
        this.cargarClientes();
       });
    }
   }

   delete(): void{
    //this.clienteService.delete(this.cliente.idCliente).subscribe( () => { });
    Swal.fire({ 
      title: '¿Desea eliminar el dato?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
     }).then((result) => {
        if(result.isConfirmed){
          this.clienteService.delete(this.cliente.idCliente).subscribe(() => { 
              this.cargarClientes();
              this.cliente = { }  as Cliente;
              Swal.fire('Eliminado', 'El cliente ha sido aliminado', 'success');
           });
        }else{
          this.cliente = { } as Cliente;
        }
      });

   }

  // interacción en la pagina web
editarCliente(cliente: Cliente): void{
  this.cliente = {...cliente};
  this.idEditar = cliente.idCliente;
  this.editar = true;

  setTimeout(() => { 
    this.formularioCliente.nativeElement.scrollIntoView({behavior: 'smooth', block: 'start'});
    }, 100);
}

editarClienteCancelar(form: NgForm): void{
  this.cliente = { } as Cliente;
  this.idEditar = null;
  this.editar = false;
  form.resetForm();
}


guardar(form: NgForm): void{
  if(this.editar && this.idEditar !== null ){
    this.update();
    form.resetForm();
  }else{
    this.save();
    form.resetForm();
  }
}

aplicarFiltro(event: Event){
  const filtro = (event.target as HTMLInputElement).value;
  this.dataSource.filter = filtro.trim().toLowerCase();
}


  

}
