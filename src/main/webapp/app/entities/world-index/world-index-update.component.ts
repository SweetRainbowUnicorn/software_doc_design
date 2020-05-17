import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorldIndex, WorldIndex } from 'app/shared/model/world-index.model';
import { WorldIndexService } from './world-index.service';

@Component({
  selector: 'jhi-world-index-update',
  templateUrl: './world-index-update.component.html'
})
export class WorldIndexUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    indexName: [null, [Validators.required, Validators.maxLength(50)]],
    lastPrice: [null, [Validators.required]],
    change: [null, [Validators.required]],
    volume: [null, [Validators.required]]
  });

  constructor(protected worldIndexService: WorldIndexService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ worldIndex }) => {
      this.updateForm(worldIndex);
    });
  }

  updateForm(worldIndex: IWorldIndex): void {
    this.editForm.patchValue({
      id: worldIndex.id,
      indexName: worldIndex.indexName,
      lastPrice: worldIndex.lastPrice,
      change: worldIndex.change,
      volume: worldIndex.volume
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const worldIndex = this.createFromForm();
    if (worldIndex.id !== undefined) {
      this.subscribeToSaveResponse(this.worldIndexService.update(worldIndex));
    } else {
      this.subscribeToSaveResponse(this.worldIndexService.create(worldIndex));
    }
  }

  private createFromForm(): IWorldIndex {
    return {
      ...new WorldIndex(),
      id: this.editForm.get(['id'])!.value,
      indexName: this.editForm.get(['indexName'])!.value,
      lastPrice: this.editForm.get(['lastPrice'])!.value,
      change: this.editForm.get(['change'])!.value,
      volume: this.editForm.get(['volume'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorldIndex>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
