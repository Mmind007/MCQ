CREATE TABLE learning_progress (
    id BIGINT PRIMARY KEY AUTO INCREMENT,
    user_id UUID REFERENCES users(id),
    skill_area_id UUID REFERENCES skill_areas(id),
    date DATE NOT NULL,
    summary TEXT,
    progress_percentage INT
);