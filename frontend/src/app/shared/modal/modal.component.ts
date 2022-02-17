import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';


@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {

  constructor(private ref: ChangeDetectorRef) {
  }

  @Input()
  modalShown!: boolean;

  @Output()
  modalShownChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  @ViewChild('modalContainer') modalContainer: any;

  ngOnInit(): void {

  }

  closeModal() {
    this.modalShown = false;
    this.modalShownChange.next(false);
  }

  onModalClick(event: any) {
    if (event.target !== this.modalContainer.nativeElement) {
      return;
    }

    this.closeModal();
  }
}
