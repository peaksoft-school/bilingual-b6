
INSERT INTO auth_info (id, email, password, role)
VALUES (1, 'admin', '$2a$12$14O0le2HrWYik9zTuLfN6OMYwElMMDnFFoMg26q4TbXgj4N5o6I1.', 'ADMIN');

INSERT INTO auth_info (id, email, password, role)
VALUES (2, 'client', '$2a$12$mtlz6KVtGHZvpRL5WjGg3OfEwcXwVHTzUszq30sko8AcT3NtFEUb.', 'CLIENT');

INSERT INTO clients (id, first_name, last_name, auth_info_id)
values (1, 'Adilet', 'Zhumakadyrov', 2);

INSERT INTO tests (id,is_active, short_description, title)
VALUES (1,true, 'select the English word the list','Take a free practice test and estimate your score');

INSERT INTO results (id, date_of_submission, final_score, status, client_id, test_id)
VALUES (1, '2022-10-14 11:01:05', 9, 'EVALUATED', 1, 1);

INSERT INTO contents (id, answer_type, content)
VALUES (1, 'AUDIO', 'Audio.mp3');

INSERT INTO questions (id, correct_answer, duration, is_active, min_number_of_words, number_of_replays, option_type,
                       passage, question_type, short_description, statement, title, content_id, test_id)
VALUES (1, 'Hello, how is it going?', 30, true, 50, 2, 'SINGLE_CHOICE', 'One of the main reasons to visit Kyrgyzstan is nature, ' ||
                                                                        'the nature is very beautiful and pristine in this country. ' ||
                                                                        'Around 94% of Kyrgyzstan is covered by mountains, which gives a ' ||
                                                                        'lot of possibilities for the expedition, hiking, and camping. ' ||
                                                                        'The traveler visiting this unique part of Central Asia will discover a fascinating variety of landscapes,' ||
                                                                        ' from spacious valleys to high-altitude glaciers.',
        'SELECT_REAL_ENGLISH_WORDS', null, 'What did resident think could happen with new bridge?', 'Respond in at least N words', 1, 1);

INSERT INTO options (id, is_true, option, question_id)
VALUES (1, true, 'audio1',1);

INSERT INTO question_answers (id, number_of_words, score, content_id, question_id, result_id)
VALUES (1, 39, 9, 1, 1, 1);

INSERT INTO question_answers_options (question_answer_id, options_id)
VALUES (1,1);