
INSERT INTO auth_info (id, email, password, role)
VALUES (1, 'admin@gmail.com', '$2a$12$14O0le2HrWYik9zTuLfN6OMYwElMMDnFFoMg26q4TbXgj4N5o6I1.', 'ADMIN');

INSERT INTO auth_info (id, email, password, role)
VALUES (2, 'client@gmail.com', '$2a$12$mtlz6KVtGHZvpRL5WjGg3OfEwcXwVHTzUszq30sko8AcT3NtFEUb.', 'CLIENT');

INSERT INTO clients (id, first_name, last_name, auth_info_id)
values (1, 'Client', 'Clientov', 2);

INSERT INTO tests (id,is_active, short_description, title)
VALUES (1,true, 'select the English word the list','Take a free practice test and estimate your score');

INSERT INTO contents (id, content_type, content)
VALUES (1, 'AUDIO', 'Audio.mp3'),
       (2, 'IMAGE', 'Image.png'),
       (3, 'TEXT', 'text.text');

INSERT INTO questions (id, correct_answer, duration, is_active, min_number_of_words, number_of_replays, option_type,
                       passage, question_type, short_description, statement, title, content_id, test_id)
VALUES (1, null, 1, true, null, null, 'MULTIPLE_CHOICE', null, 'SELECT_REAL_ENGLISH_WORDS', null, null, 'Select real english word', 3, 1),
       (2, null, 1, true, null, null, 'MULTIPLE_CHOICE', null, 'LISTEN_AND_SELECT_WORD', null, null, 'Select real english word', 1, 1),
       (3, 'Hello, how is it going?', 1, true, null, 2, null, null, 'TYPE_WHAT_YOU_HEAR', null, null, 'Write what are listening', 1, 1),
       (4, 'Protecting nature means protecting the Motherland', 1, true, null, null, null, null, 'DESCRIBE_IMAGE', null, null, 'Write one more sentences that describe the images', 2, 1),
       (5, 'mai ankyl iz ət wɜːk', 1, true, null, null, null, null, 'RECORD_SAYING_STATEMENT', null, 'poka z ne znau shto write', 'Record yourself saying the statement', 1, 1),
       (6, null, 1, true, 50, null, null, null, 'RESPOND_IN_AT_LEAST_N_WORDS', null, 'Describe a time you were surprised? What happened?', 'Respond to the question at least 30 word', 3, 1),
       (7, 'Meta Platforms is an American multinational holding company that owns a technology conglomerate and is located in Menlo Park, California.',
        1, true, null, null, null, 'Meta Platforms is an American multinational holding company that owns a technology conglomerate and is located in Menlo Park, California. Facebook Instagram, WhatsApp and Oculus are the parent organization. Facebook Facebook is one of the most expensive companies in the world, and is also considered one of the "big five" companies in the field of information technology in the USA, along with Amazon, Alphabet (owned by Google), Meta offers other products and services, including Facebook Messenger, Facebook Watch and Facebook Portal, the company also acquired Giphy and Mapillary, has 9.99 % of shares in Jio Platforms.',
        'HIGHLIGHT_THE_ANSWER', null, 'What is Meta and where is located?', 'Highlight the answer to the question below', 3, 1),
       (8, null, 1, true, null, null, 'SINGLE_CHOICE', 'We study one of the modern languages at school. It is English. It is my favourite subject. At the lessons of English we learn to read, write and speak. We learn the History and Geography of bur country, Great Britain and the USA. We read stories after famous English and American children is writers. I like stories after Alan Milne, Donald Bisset, Lewis Carroll, Mark Twain and others. I want to be clever at English because English will help me in my future life. I shall read books in English, watch films and listen to songs and understand them. But what is more important, I shall speak with people from other countries and we will understand each other. We will make friends and will live in peace.',
        'SELECT_MAIN_IDEA', null, null, 'Select the main idea that is expressed in the passage', 3, 1),
       (9, null, 1, true, null, null, 'SINGLE_CHOICE', 'A programming language is a system of notation for writing computer programs. Most programming languages are text-based formal languages, but they may also be graphical. They are a kind of computer language. The description of a programming language is usually split into the two components of syntax (form) and semantics (meaning), which are usually defined by a formal language. Some languages are defined by a specification document (for example, the C programming language is specified by an ISO Standard) while other languages (such as Perl) have a dominant implementation that is treated as a reference. Some languages have both, with the basic language defined by a standard and extensions taken from the dominant implementation being common. Programming language theory is a subfield of computer science that deals with the design, implementation, analysis, characterization, and classification of programming languages.',
        'SELECT_BEST_TITLE', null, null, 'Select the best title for the passage', 3, 1);

INSERT INTO options (id, is_true, option)
VALUES (1, true, 'string'),
       (2, false, 'intejer'),
       (3, true, 'boolean'),
       (4, false, 'cshar'),

       (5, true, 'word1'),
       (6, true, 'word2'),
       (7, false, 'word3'),
       (8, false, 'word4'),

       (9, true, 'This text is written about the English subject and shows that English is needed and relevant everywhere'),
       (10, false, 'Drawing is an interesting subject, too. I''m fond of drawing and painting. When we draw, we make pictures with a pen or chalk.'),
       (11, false, 'I do not understand this text!!!!'),

       (12, false, 'English language'),
       (13, false, 'My family'),
       (14, true, 'Programming language');

INSERT INTO questions_options(question_id, options_id)
VALUES (1,1),
       (1,2),
       (1,3),
       (1,4),
       (2,5),
       (2,6),
       (2,7),
       (2,8),
       (8,9),
       (8,10),
       (8,11),
       (9,12),
       (9,13),
       (9,14);

INSERT INTO question_answers (id, number_of_words, score, content_id, question_id, result_id)
VALUES (1, null, 5, 3, 1, null),
       (2, null, 5, 1, 2, null),
       (3, null, 5, 1, 3, null),
       (4, null, 7, 2, 4, null),
       (5, null, 4, 1, 5, null),
       (6, 14, 9, 3, 6, null),
       (7, null, 3, 3, 7, null),
       (8, null, 10, 3, 8, null),
       (9, null, 0, 3, 9, null);

INSERT INTO question_answers_options (question_answer_id, options_id)
VALUES (1,1),
       (1,2),
       (2,5),
       (2,7),
       (8,11),
       (9,14);