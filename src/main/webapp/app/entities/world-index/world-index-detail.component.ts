import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorldIndex } from 'app/shared/model/world-index.model';

@Component({
  selector: 'jhi-world-index-detail',
  templateUrl: './world-index-detail.component.html'
})
export class WorldIndexDetailComponent implements OnInit {
  worldIndex: IWorldIndex | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ worldIndex }) => (this.worldIndex = worldIndex));
  }

  previousState(): void {
    window.history.back();
  }
}
