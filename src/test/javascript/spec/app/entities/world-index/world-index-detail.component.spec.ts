import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YahoofinanceTestModule } from '../../../test.module';
import { WorldIndexDetailComponent } from 'app/entities/world-index/world-index-detail.component';
import { WorldIndex } from 'app/shared/model/world-index.model';

describe('Component Tests', () => {
  describe('WorldIndex Management Detail Component', () => {
    let comp: WorldIndexDetailComponent;
    let fixture: ComponentFixture<WorldIndexDetailComponent>;
    const route = ({ data: of({ worldIndex: new WorldIndex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YahoofinanceTestModule],
        declarations: [WorldIndexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WorldIndexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorldIndexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load worldIndex on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.worldIndex).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
