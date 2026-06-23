-- Запись для ImageModelServiceTest: url совпадает с тем, куда складывает saveImage/get читает (корень images/...)
INSERT INTO image_model (id, url, image_type) VALUES
    (901, 'images/integration/901.bin', 'RECIPE');
SELECT setval('image_model_id_seq', (SELECT COALESCE(MAX(id), 1) FROM image_model), true);
